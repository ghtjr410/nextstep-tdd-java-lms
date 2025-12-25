package nextstep.courses.domain.image;

public class ImageFile {
    private static final long MAX_FILE_SIZE = 1024 * 1024;

    private final String filename;
    private final long fileSize;
    private final ImageType imageType;

    public ImageFile(String filename, long fileSize) {
        validateFileSize(fileSize);
        this.filename = filename;
        this.fileSize = fileSize;
        this.imageType = ImageType.from(extractExtension(filename));
    }

    private void validateFileSize(long fileSize) {
        if (fileSize > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(String.format("이미지 크기는 1MB 이하여야 합니다. (입력: %d bytes)", fileSize));
        }
    }

    private String extractExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            throw new IllegalArgumentException("파일 확장자가 없습니다. (입력: " + filename + ")");
        }
        return filename.substring(dotIndex + 1);
    }
}
