package nextstep.courses.domain.image;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageTypeTest {

    @ParameterizedTest(name = "입력값:{0}")
    @ValueSource(strings = {"png", "PNG"})
    void from_정상입력_변환성공(String input) {
        assertThat(ImageType.from(input)).isEqualTo(ImageType.PNG);
    }

    @Test
    void from_JPEG입력_JPG반환() {
        assertThat(ImageType.from("jpeg")).isEqualTo(ImageType.JPG);
    }

    @Test
    void from_지원하지않는타입_예외발생() {
        assertThatThrownBy(() -> ImageType.from("bmp")).isInstanceOf(IllegalArgumentException.class);
    }
}
