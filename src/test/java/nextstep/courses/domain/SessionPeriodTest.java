package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionPeriodTest {
    @Test
    void 생성자_정상입력_생성성공() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 2);

        assertThatCode(() -> new SessionPeriod(start, end)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_종료일이시작일이전_예외발생() {
        LocalDate start = LocalDate.of(2024, 1, 2);
        LocalDate end = LocalDate.of(2024, 1, 1);

        assertThatThrownBy(() -> new SessionPeriod(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일 이후");
    }
}
