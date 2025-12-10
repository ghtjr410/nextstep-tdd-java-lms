package nextstep.courses.domain;

public abstract class Session {
    private final Long id;
    private final CoverImage coverImage;
    private final SessionPeriod period;
    private final SessionStatus status;
    private final Enrollments enrollments;

    public Session(CoverImage coverImage, SessionPeriod period) {
        this(0L, coverImage, period, SessionStatus.PREPARING, new Enrollments());
    }

    public Session(CoverImage coverImage, SessionPeriod period, SessionStatus status) {
        this(0L, coverImage, period, status, new Enrollments());
    }

    public Session(
            Long id, CoverImage coverImage, SessionPeriod period, SessionStatus status, Enrollments enrollments) {
        this.id = id;
        this.coverImage = coverImage;
        this.period = period;
        this.status = status;
        this.enrollments = enrollments;
    }

    public final void enroll(Enrollment enrollment, Money payment) {
        validateStatus();
        validatePaymentPolicy(payment);
        validateCapacityPolicy();
        enrollments.add(enrollment);
    }

    protected void validateStatus() {
        if (!status.canEnroll()) {
            throw new IllegalStateException(String.format("모집중인 강의만 수강 신청이 가능합니다. (현재 상태: %s)", status));
        }
    }

    protected abstract void validatePaymentPolicy(Money payment);

    protected abstract void validateCapacityPolicy();

    public int enrollmentCount() {
        return enrollments.count();
    }

    public SessionStatus getStatus() {
        return this.status;
    }
}
