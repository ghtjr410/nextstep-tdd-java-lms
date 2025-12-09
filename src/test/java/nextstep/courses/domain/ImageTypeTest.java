package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageTypeTest {

    @Test
    void from_JPEG입력_JPG반환() {
        assertThat(ImageType.from("jpeg")).isEqualTo(ImageType.JPG);
    }

    @Test
    void from_지원하지않는타입_예외발생() {
        assertThatThrownBy(() -> ImageType.from("bmp")).isInstanceOf(IllegalArgumentException.class);
    }
}
