package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Enrollments {
    private final List<Enrollment> values;

    public Enrollments() {
        this(new ArrayList<>());
    }

    public Enrollments(List<Enrollment> values) {
        this.values = new ArrayList<>(values);
    }

    public void add(Enrollment enrollment) {
        this.values.add(enrollment);
    }

    public int count() {
        return this.values.size();
    }
}
