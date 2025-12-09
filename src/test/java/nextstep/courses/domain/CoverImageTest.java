package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CoverImageTest {

    @Test
    void 생성자_정상입력_생성성공() {
        assertThatCode(() -> new CoverImage("image.png", 1024 * 1024, ImageType.PNG, 300, 200))
                .doesNotThrowAnyException();
    }

    @Test
    void 생성자_파일크기초과_예외발생() {
        long overSize = 1024 * 1024 + 1;
        assertThatThrownBy(() -> new CoverImage("image.png", overSize, ImageType.PNG, 300, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 크기는 1MB 이하여야 합니다.");
    }

    @Test
    void 생성_확장자없음_예외발생() {
        assertThatThrownBy(() -> new CoverImage("image", 1024, 300, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("파일 확장자가 없습니다");
    }

    @Test
    void 생성_너비부족_예외발생() {
        assertThatThrownBy(() -> new CoverImage("image.png", 1024, ImageType.PNG, 299, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("너비는 300픽셀 이상");
    }

    @Test
    void 생성_높이부족_예외발생() {
        assertThatThrownBy(() -> new CoverImage("image.png", 1024, ImageType.PNG, 300, 199))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("높이는 200픽셀 이상");
    }

    @Test
    void 생성_비율불일치_예외발생() {
        assertThatThrownBy(() -> new CoverImage("image.png", 1024, ImageType.PNG, 300, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비율은 3:2");
    }
}
