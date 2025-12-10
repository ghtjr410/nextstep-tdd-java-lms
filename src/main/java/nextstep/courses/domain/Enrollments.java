package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Enrollments {
    private final List<Enrollment> enrollments;

    public Enrollments() {
        this(new ArrayList<>());
    }

    public Enrollments(List<Enrollment> enrollments) {
        this.enrollments = new ArrayList<>(enrollments);
    }

    public void add(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
}
