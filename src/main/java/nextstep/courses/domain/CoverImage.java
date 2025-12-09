package nextstep.courses.domain;

public class CoverImage {
    private static final long MAX_FILE_SIZE = 1024 * 1024;

    private String filename;
    private long fileSize;
    private ImageType imageType;
    private int width;
    private int height;

    public CoverImage(String filename, long fileSize, ImageType imageType, int width, int height) {
        validateFileSize(fileSize);
        this.filename = filename;
        this.fileSize = fileSize;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private void validateFileSize(long fileSize) {
        if (fileSize > MAX_FILE_SIZE)
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다. (입력: %d bytes)", fileSize);
    }
}
