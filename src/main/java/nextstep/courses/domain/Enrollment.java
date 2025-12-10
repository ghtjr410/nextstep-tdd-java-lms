package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private Long sessionId;
    private Long studentId;
    private LocalDateTime createdAt;

    public Enrollment(Long sessionId, Long studentId, LocalDateTime now) {
        this(0L, sessionId, studentId, now);
    }

    public Enrollment(Long id, Long sessionId, Long studentId, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }
}
