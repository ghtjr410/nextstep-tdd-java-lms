package nextstep.courses.domain.session.image;

import java.util.ArrayList;
import java.util.List;

public class CoverImages {
    private static final int MIN_SIZE = 1;

    public final List<CoverImage> values;

    public CoverImages(List<CoverImage> values) {
        validate(values);
        this.values = new ArrayList<>(values);
    }

    private void validate(List<CoverImage> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException(String.format("커버 이미지는 최소 %d개 이상이어야 합니다.", MIN_SIZE));
        }
    }

    public void add(CoverImage coverImage) {
        this.values.add(coverImage);
    }

    public int size() {
        return values.size();
    }

    public List<CoverImage> getValues() {
        return new ArrayList<>(values);
    }
}
