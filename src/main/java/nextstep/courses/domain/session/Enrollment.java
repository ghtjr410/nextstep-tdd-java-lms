package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private final Long studentId;
    private final LocalDateTime createdAt;

    public Enrollment(Long sessionId, Long studentId, LocalDateTime now) {
        this(0L, sessionId, studentId, now);
    }

    public Enrollment(Long id, Long sessionId, Long studentId, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
