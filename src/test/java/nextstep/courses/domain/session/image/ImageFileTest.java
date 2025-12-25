package nextstep.courses.domain.session.image;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageFileTest {
    @Test
    void 생성자_정상입력_생성성공() {
        assertThatCode(() -> new ImageFile("image.png", 1024 * 1024)).doesNotThrowAnyException();
    }

    @Test
    void 생성자_파일크기초과_예외발생() {
        long overSize = 1024 * 1024 + 1;

        assertThatThrownBy(() -> new ImageFile("image.png", overSize))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 크기는 1MB 이하여야 합니다.");
    }

    @Test
    void 생성자_확장자없음_예외발생() {
        assertThatThrownBy(() -> new ImageFile("image", 1024 * 1024))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("파일 확장자가 없습니다");
    }
}
