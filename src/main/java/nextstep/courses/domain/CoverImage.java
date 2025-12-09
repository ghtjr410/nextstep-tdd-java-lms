package nextstep.courses.domain;

public class CoverImage {
    private static final long MAX_FILE_SIZE = 1024 * 1024;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private String filename;
    private long fileSize;
    private ImageType imageType;
    private int width;
    private int height;

    public CoverImage(String filename, long fileSize, ImageType imageType, int width, int height) {
        validateFileSize(fileSize);
        validateDimension(width, height);
        this.filename = filename;
        this.fileSize = fileSize;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private void validateFileSize(long fileSize) {
        if (fileSize > MAX_FILE_SIZE)
            throw new IllegalArgumentException(String.format("이미지 크기는 1MB 이하여야 합니다. (입력: %d bytes)", fileSize));
    }

    private void validateDimension(int width, int height) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException(String.format("이미지 너비는 %d픽셀 이상이어야 합니다. (입력: %d)", MIN_WIDTH, width));
        }
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException(String.format("이미지 높이는 %d픽셀 이상이어야 합니다. (입력: %d)", MIN_HEIGHT, height));
        }
    }
}
