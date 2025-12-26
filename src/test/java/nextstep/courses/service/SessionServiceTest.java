package nextstep.courses.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.SessionStatus;
import nextstep.courses.domain.session.policy.PaidEnrollmentPolicy;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(
                "insert into course (id, title, creator_id, created_at) values (?, ?, ?, ?)",
                1L,
                "TDD 강의",
                1L,
                java.sql.Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void enroll_수강신청_성공() {
        // given
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        Session session = new Session(1L, null, period, SessionStatus.RECRUITING, new PaidEnrollmentPolicy(30, 50000));
        Long sessionId = sessionRepository.save(session);
        Payment payment = new Payment("1", sessionId, 1L, 50000L);

        // when
        sessionService.enroll(sessionId, 1L, payment);

        // then
        Session found = sessionRepository.findById(sessionId).get();
        assertThat(found.enrollmentCount()).isEqualTo(1);
    }

    @Test
    void enroll_존재하지_않는_강의_수강신청시_예외() {
        // given
        Long invalidSessionId = 999L;
        Payment payment = new Payment("1", invalidSessionId, 1L, 50000L);

        // when & then
        assertThatThrownBy(() -> sessionService.enroll(invalidSessionId, 1L, payment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강의를 찾을 수 없습니다.");
    }
}
