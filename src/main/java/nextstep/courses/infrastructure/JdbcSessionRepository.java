package nextstep.courses.infrastructure;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.image.CoverImages;
import nextstep.courses.domain.session.policy.EnrollmentPolicy;
import nextstep.courses.domain.session.policy.FreeEnrollmentPolicy;
import nextstep.courses.domain.session.policy.PaidEnrollmentPolicy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSessionRepository implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        Long sessionId = insertSession(session);

        if (session.getCoverImages() != null) {
            for (CoverImage coverImage : session.getCoverImages().getValues()) {
                insertCoverImage(coverImage, sessionId);
            }
        }

        return sessionId;
    }

    private long insertSession(Session session) {
        final Integer maxEnrollment;
        final Integer price;

        if (session.getType() == SessionType.PAID) {
            PaidEnrollmentPolicy paid = (PaidEnrollmentPolicy) session.getEnrollmentPolicy();
            maxEnrollment = paid.getCapacity().getValue();
            price = paid.getPrice().getAmount();
        } else {
            maxEnrollment = null;
            price = null;
        }

        String sql =
                "insert into session (course_id, session_type, progress_status, recruitment_status, start_date, end_date, max_enrollment, price, created_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                    ps.setLong(1, session.getCourseId());
                    ps.setString(2, session.getType().name());
                    ps.setString(3, session.getProgressStatus().name());
                    ps.setString(4, session.getRecruitmentStatus().name());
                    ps.setDate(5, Date.valueOf(session.getStartDate()));
                    ps.setDate(6, Date.valueOf(session.getEndDate()));
                    ps.setObject(7, maxEnrollment);
                    ps.setObject(8, price);
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                },
                keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void insertCoverImage(CoverImage coverImage, Long sessionId) {
        String sql =
                "insert into cover_image (session_id, filename, file_size, width, height, image_type) values (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                sessionId,
                coverImage.getFilename(),
                coverImage.getFileSize(),
                coverImage.getWidth(),
                coverImage.getHeight(),
                coverImage.getImageType().name());
    }

    @Override
    public void saveEnrollment(Enrollment enrollment) {
        String sql = "insert into enrollment (session_id, student_id, created_at) values (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                enrollment.getSessionId(),
                enrollment.getStudentId(),
                Timestamp.valueOf(enrollment.getCreatedAt()));
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select * from session where id = ?";
        List<Session> sessions = jdbcTemplate.query(sql, rowMapper(), id);
        return sessions.stream().findFirst();
    }

    @Override
    public List<Session> findByCourseId(Long courseId) {
        String sql = "select * from session where course_id = ?";
        return jdbcTemplate.query(sql, rowMapper(), courseId);
    }

    private RowMapper<Session> rowMapper() {
        return (rs, rowNum) -> {
            Long sessionId = rs.getLong("id");
            String type = rs.getString("session_type");
            EnrollmentPolicy policy = createPolicy(type, rs.getInt("max_enrollment"), rs.getInt("price"));
            CoverImages coverImages = findCoverImageBySessionId(sessionId);
            Enrollments enrollments = findEnrollmentsBySessionId(sessionId);

            return new Session(
                    sessionId,
                    rs.getLong("course_id"),
                    coverImages,
                    new SessionPeriod(
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate()),
                    ProgressStatus.valueOf(rs.getString("progress_status")),
                    RecruitmentStatus.valueOf(rs.getString("recruitment_status")),
                    policy,
                    enrollments);
        };
    }

    private EnrollmentPolicy createPolicy(String type, int maxEnrollment, int price) {
        if (SessionType.PAID.name().equals(type)) {
            return new PaidEnrollmentPolicy(maxEnrollment, price);
        }
        return new FreeEnrollmentPolicy();
    }

    private CoverImages findCoverImageBySessionId(Long sessionId) {
        String sql = "select * from cover_image where session_id = ?";
        List<CoverImage> images = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new CoverImage(
                        rs.getString("filename"), rs.getLong("file_size"), rs.getInt("width"), rs.getInt("height")),
                sessionId);

        if (images.isEmpty()) {
            return null;
        }

        return new CoverImages(images);
    }

    private Enrollments findEnrollmentsBySessionId(Long sessionId) {
        String sql = "select * from enrollment where session_id = ?";
        List<Enrollment> list = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Enrollment(
                        rs.getLong("id"),
                        rs.getLong("session_id"),
                        rs.getLong("student_id"),
                        null,
                        rs.getTimestamp("created_at").toLocalDateTime()),
                sessionId);

        return new Enrollments(list);
    }
}
