package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.policy.PaidEnrollmentPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);

        jdbcTemplate.update(
                "insert into course (id, title, creator_id, created_at) values (?, ?, ?, ?)",
                1L,
                "TDD 강의",
                1L,
                java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
    }

    @Test
    void 커버이미지포함_저장_후_조회() {
        Session session = SessionBuilder.aSession()
                .withCoverImage("image.png", 1024, 300, 200)
                .build();

        Long sessionId = sessionRepository.save(session);
        Session found = sessionRepository.findById(sessionId).get();

        assertThat(found.getCoverImages().size()).isEqualTo(1);
        assertThat(found.getCoverImages().getValues())
                .extracting(CoverImage::getFilename)
                .contains("image.png");
    }

    @Test
    void 무료강의_저장_후_조회() {
        Session session = SessionBuilder.aSession().withFreePolicy().build();

        Long sessionId = sessionRepository.save(session);
        Session found = sessionRepository.findById(sessionId).get();

        assertThat(found.getProgressStatus()).isEqualTo(ProgressStatus.PREPARING);
        assertThat(found.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.NOT_RECRUITING);
    }

    @Test
    void 유료강의_저장_후_조회() {
        Session session = SessionBuilder.aSession().withPaidPolicy(30, 50000).build();

        Long sessionId = sessionRepository.save(session);
        Session found = sessionRepository.findById(sessionId).get();

        PaidEnrollmentPolicy policy = (PaidEnrollmentPolicy) found.getEnrollmentPolicy();
        assertThat(found.getType()).isEqualTo(SessionType.PAID);
        assertThat(policy.getCapacity().getValue()).isEqualTo(30);
        assertThat(policy.getPrice().getAmount()).isEqualTo(50000);
    }

    @Test
    void 수강신청_저장_후_조회() {
        Session session = SessionBuilder.aSession().withPaidPolicy(30, 50000).build();
        Long sessionId = sessionRepository.save(session);
        Enrollment enrollment = new Enrollment(sessionId, 1L, LocalDateTime.now());

        sessionRepository.saveEnrollment(enrollment);
        Session found = sessionRepository.findById(sessionId).get();

        assertThat(found.enrollmentCount()).isEqualTo(1);
    }

    @Test
    void 수강신청_상태_업데이트() {
        // given
        Session session = SessionBuilder.aSession()
                .withProgressStatus(ProgressStatus.IN_PROGRESS)
                .withRecruitmentStatus(RecruitmentStatus.RECRUITING)
                .withPaidPolicy(30, 50000)
                .build();
        Long sessionId = sessionRepository.save(session);
        Enrollment enrollment = new Enrollment(sessionId, 1L, LocalDateTime.now());
        sessionRepository.saveEnrollment(enrollment);

        enrollment.approve();
        sessionRepository.updateEnrollment(enrollment);

        Session found = sessionRepository.findById(sessionId).get();
        assertThat(found.getEnrollments().getValues())
                .hasSize(1)
                .extracting(Enrollment::getStatus)
                .containsExactly(EnrollmentStatus.APPROVED);
    }

    @Test
    void courseId로_조회() {
        sessionRepository.save(SessionBuilder.aSession().withFreePolicy().build());
        sessionRepository.save(
                SessionBuilder.aSession().withPaidPolicy(10, 30000).build());

        List<Session> sessions = sessionRepository.findByCourseId(1L);

        assertThat(sessions).hasSize(2);
    }
}
