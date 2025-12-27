package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private final Long studentId;
    private EnrollmentStatus status;
    private final LocalDateTime createdAt;

    public Enrollment(Long sessionId, Long studentId, LocalDateTime now) {
        this(0L, sessionId, studentId, EnrollmentStatus.PENDING, now);
    }

    public Enrollment(Long id, Long sessionId, Long studentId, EnrollmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void approve() {
        this.status = EnrollmentStatus.APPROVED;
    }

    public void reject() {
        this.status = EnrollmentStatus.REJECTED;
    }

    public boolean isApproved() {
        return status == EnrollmentStatus.APPROVED;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
