package nextstep.courses.domain.session.image;

public enum ImageType {
    GIF,
    JPG,
    PNG,
    SVG;

    public static ImageType from(String extension) {
        String upper = extension.toUpperCase();
        if (upper.equals("JPEG")) {
            return JPG;
        }
        return valueOf(upper);
    }
}
