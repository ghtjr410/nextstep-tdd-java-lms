package nextstep.courses.domain.session.image;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CoverImagesTest {

    @ParameterizedTest
    @MethodSource("유효한_이미지_목록")
    void 생성자_이미지1개이상이면_생성성공(List<CoverImage> images) {
        CoverImages coverImages = new CoverImages(images);

        assertThat(coverImages.size()).isEqualTo(images.size());
    }

    static Stream<List<CoverImage>> 유효한_이미지_목록() {
        CoverImage image1 = new CoverImage("image1.png", 1024, 300, 200);
        CoverImage image2 = new CoverImage("image2.png", 2048, 600, 400);

        return Stream.of(
                List.of(image1), // 경계값: 1개
                List.of(image1, image2) // 경계값: 2개
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 생성자_빈값_예외발생(List<CoverImage> inputs) {
        assertThatThrownBy(() -> new CoverImages(inputs))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 1개 이상");
    }
}
