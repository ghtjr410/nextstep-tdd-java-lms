package nextstep.courses.domain;

public enum ImageType {
    GIF,
    JPG,
    PNG,
    SVG;

    public static ImageType from(String extension) {
        if (extension.equals("jpeg")) {
            return JPG;
        }
        return valueOf(extension);
    }
}
