package nextstep.courses.domain.session.image;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageDimensionTest {
    @Test
    void 생성자_정상입력_생성성공() {
        assertThatCode(() -> new ImageDimension(300, 200)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_너비부족_예외발생() {
        assertThatThrownBy(() -> new ImageDimension(299, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("너비는 300픽셀 이상");
    }

    @Test
    void 생성자_높이부족_예외발생() {
        assertThatThrownBy(() -> new ImageDimension(300, 199))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("높이는 200픽셀 이상");
    }

    @Test
    void 생성자_비율불일치_예외발생() {
        assertThatThrownBy(() -> new ImageDimension(300, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비율은 3:2");
    }
}
