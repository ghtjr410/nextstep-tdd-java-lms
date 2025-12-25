package nextstep.courses.domain.session.image;

public class CoverImage {
    private final ImageFile imageFile;
    private final ImageDimension dimension;

    public CoverImage(String filename, long fileSize, int width, int height) {
        this.imageFile = new ImageFile(filename, fileSize);
        this.dimension = new ImageDimension(width, height);
    }

    public String getFilename() {
        return imageFile.getFilename();
    }

    public long getFileSize() {
        return imageFile.getFileSize();
    }

    public ImageType getImageType() {
        return imageFile.getImageType();
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }
}
