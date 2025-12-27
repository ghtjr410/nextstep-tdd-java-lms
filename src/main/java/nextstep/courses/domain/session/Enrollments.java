package nextstep.courses.domain.session;

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
        validateNotDuplicate(enrollment);
        this.values.add(enrollment);
    }

    private void validateNotDuplicate(Enrollment enrollment) {
        boolean exists = values.stream().anyMatch(e -> e.getStudentId().equals(enrollment.getStudentId()));
        if (exists) {
            throw new IllegalArgumentException("이미 수강 신청한 강의입니다.");
        }
    }

    public int count() {
        return this.values.size();
    }
}
