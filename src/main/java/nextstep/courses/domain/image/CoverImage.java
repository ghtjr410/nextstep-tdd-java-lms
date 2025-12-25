package nextstep.courses.domain.image;

public class CoverImage {
    private final ImageFile imageFile;
    private final ImageDimension dimension;

    public CoverImage(String filename, long fileSize, int width, int height) {
        this.imageFile = new ImageFile(filename, fileSize);
        this.dimension = new ImageDimension(width, height);
    }
}
