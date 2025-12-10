package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CapacityTest {

    @Test
    void 생성자_정상입력_생성성공() {
        assertThatCode(() -> new Capacity(1)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_0명이하_예외발생() {
        assertThatThrownBy(() -> new Capacity(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 수강 인원은 1명 이상");
    }

    @ParameterizedTest(name = "정원 {0}명 / 현재 {1}명 → 초과 여부: {2}")
    @CsvSource({"10, 10, true", "10, 9, false"})
    void isOver_정원초과여부_확인(int capacityValue, int currentCount, boolean expected) {
        boolean result = new Capacity(capacityValue).isOver(currentCount);

        assertThat(result).isEqualTo(expected);
    }
}
