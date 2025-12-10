package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private Long studentId;
    private LocalDateTime createdAt;

    public Enrollment(Long studentId, LocalDateTime now) {
        this(0L, studentId, now);
    }

    public Enrollment(Long id, Long studentId, LocalDateTime createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }
}
