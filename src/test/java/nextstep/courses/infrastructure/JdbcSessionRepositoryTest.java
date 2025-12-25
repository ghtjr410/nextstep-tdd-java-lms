package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.session.policy.FreeEnrollmentPolicy;
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
class JdbcSessionRepositoryTest {

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
    void 무료강의_저장_후_조회() {
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        Session session = new Session(null, period, new FreeEnrollmentPolicy());

        Long sessionId = sessionRepository.save(session, 1L);
        Session found = sessionRepository.findById(sessionId).get();

        assertThat(found.getType()).isEqualTo(SessionType.FREE);
        assertThat(found.getStatus()).isEqualTo(SessionStatus.PREPARING);
    }

    @Test
    void 유료강의_저장_후_조회() {
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        Session session = new Session(null, period, new PaidEnrollmentPolicy(30, 50000));

        Long sessionId = sessionRepository.save(session, 1L);
        Session found = sessionRepository.findById(sessionId).get();

        PaidEnrollmentPolicy policy = (PaidEnrollmentPolicy) found.getEnrollmentPolicy();
        assertThat(found.getType()).isEqualTo(SessionType.PAID);
        assertThat(policy.getCapacity().getValue()).isEqualTo(30);
        assertThat(policy.getPrice().getAmount()).isEqualTo(50000);
    }

    @Test
    void courseId로_조회() {
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        sessionRepository.save(new Session(null, period, new FreeEnrollmentPolicy()), 1L);
        sessionRepository.save(new Session(null, period, new PaidEnrollmentPolicy(10, 30000)), 1L);

        List<Session> sessions = sessionRepository.findByCourseId(1L);

        assertThat(sessions).hasSize(2);
    }
}
