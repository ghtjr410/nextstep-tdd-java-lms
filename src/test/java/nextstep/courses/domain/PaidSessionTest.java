package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PaidSessionTest {

    private CoverImage coverImage;
    private SessionPeriod period;
    private Money fee;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage("image.png", 1024, 300, 200);
        period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        fee = new Money(50000);
    }

    @Test
    void 생성자_정상입력_생성성공() {
        assertThatCode(() -> new PaidSession(coverImage, period, 30, fee)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_최대인원0명_예외발생() {
        CoverImage coverImage = new CoverImage("image.png", 1024, 300, 200);
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        Money fee = new Money(50000);

        assertThatThrownBy(() -> new PaidSession(coverImage, period, 0, fee))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 수강 인원은 1명 이상");
    }

    @Test
    void enroll_정상입력_성공() {
        PaidSession session = new PaidSession(coverImage, period, SessionStatus.RECRUITING, 3, fee);
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());
        Money payment = new Money(50000);

        session.enroll(enrollment, payment);

        assertThat(session.enrollmentCount()).isEqualTo(1);
    }

    @Test
    void enroll_결제금액불일치_예외발생() {
        PaidSession session = new PaidSession(coverImage, period, SessionStatus.RECRUITING, 3, fee);
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());
        Money wrongPayment = new Money(30000);

        assertThatThrownBy(() -> session.enroll(enrollment, wrongPayment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("결제 금액이 수강료와 일치하지 않습니다");
    }

    @Test
    void enroll_인원초과_예외발생() {
        PaidSession session = new PaidSession(coverImage, period, SessionStatus.RECRUITING, 2, fee);
        Money payment = new Money(50000);

        session.enroll(new Enrollment(1L, 1L, LocalDateTime.now()), payment);
        session.enroll(new Enrollment(2L, 2L, LocalDateTime.now()), payment);

        assertThatThrownBy(() -> session.enroll(new Enrollment(3L, 3L, LocalDateTime.now()), payment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("수강 인원이 초과");
    }
}
