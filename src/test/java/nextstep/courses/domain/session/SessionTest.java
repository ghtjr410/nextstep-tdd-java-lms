package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.policy.FreeEnrollmentPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionTest {
    private CoverImage coverImage;
    private SessionPeriod period;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage("image.png", 1024, 300, 200);
        period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
    }

    @Test
    void 생성자_정상입력_생성성공() {
        Session session = new Session(1L, coverImage, period, new FreeEnrollmentPolicy());

        assertThat(session.getStatus()).isEqualTo(SessionStatus.PREPARING);
        assertThat(session.getType()).isEqualTo(SessionType.FREE);
    }

    @Test
    void enroll_모집중_성공() {
        Session session = new Session(1L, coverImage, period, SessionStatus.RECRUITING, new FreeEnrollmentPolicy());
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        session.enroll(enrollment, Money.ZERO);

        assertThat(session.enrollmentCount()).isEqualTo(1);
    }

    @ParameterizedTest(name = "상태:{0}")
    @EnumSource(
            value = SessionStatus.class,
            names = {"PREPARING", "CLOSED"})
    void enroll_모집중이아닐시_예외발생(SessionStatus status) {
        Session session = new Session(1L, coverImage, period, status, new FreeEnrollmentPolicy());

        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThatThrownBy(() -> session.enroll(enrollment, Money.ZERO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("모집중인 강의만");
    }
}
