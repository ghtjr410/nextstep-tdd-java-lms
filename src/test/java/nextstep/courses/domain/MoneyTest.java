package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MoneyTest {

    @ParameterizedTest(name = "입력값:{0}")
    @ValueSource(ints = {0, 10000})
    void 생성자_정상입력_생성성공(int input) {
        assertThatCode(() -> new Money(input)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_음수금액_예외발생() {
        assertThatThrownBy(() -> new Money(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("금액은 0 이상");
    }
}
