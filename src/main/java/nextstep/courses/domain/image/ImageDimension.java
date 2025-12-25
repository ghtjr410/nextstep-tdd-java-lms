package nextstep.courses.domain.image;

public class ImageDimension {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int WIDTH_RATIO = 3;
    private static final int HEIGHT_RATIO = 2;

    private final int width;
    private final int height;

    public ImageDimension(int width, int height) {
        validateDimension(width, height);
        validateAspectRatio(width, height);
        this.width = width;
        this.height = height;
    }

    private void validateDimension(int width, int height) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException(String.format("이미지 너비는 %d픽셀 이상이어야 합니다. (입력: %d)", MIN_WIDTH, width));
        }
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException(String.format("이미지 높이는 %d픽셀 이상이어야 합니다. (입력: %d)", MIN_HEIGHT, height));
        }
    }

    private void validateAspectRatio(int width, int height) {
        if (width * HEIGHT_RATIO != height * WIDTH_RATIO) {
            throw new IllegalArgumentException(String.format("이미지 비율은 3:2여야 합니다. (입력: %d:%d)", width, height));
        }
    }
}
