package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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
}
