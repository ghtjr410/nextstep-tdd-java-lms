package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PaidSessionTest {
    @Test
    void 생성자_정상입력_생성성공() {
        // given
        CoverImage coverImage = new CoverImage("image.png", 1024, 300, 200);
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
        Money fee = new Money(50000);

        PaidSession session = new PaidSession(coverImage, period, 30, fee);

        assertThat(session.getStatus()).isEqualTo(SessionStatus.PREPARING);
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
}
