package nextstep.courses.domain.session;

import java.time.LocalDate;
import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.policy.EnrollmentPolicy;

public class Session {
    private final Long id;
    private final CoverImage coverImage;
    private final SessionPeriod period;
    private final SessionStatus status;
    private final EnrollmentPolicy enrollmentPolicy;
    private final Enrollments enrollments;

    public Session(CoverImage coverImage, SessionPeriod period, EnrollmentPolicy policy) {
        this(0L, coverImage, period, SessionStatus.PREPARING, policy, new Enrollments());
    }

    public Session(CoverImage coverImage, SessionPeriod period, SessionStatus status, EnrollmentPolicy policy) {
        this(0L, coverImage, period, status, policy, new Enrollments());
    }

    public Session(
            Long id,
            CoverImage coverImage,
            SessionPeriod period,
            SessionStatus status,
            EnrollmentPolicy policy,
            Enrollments enrollments) {
        this.id = id;
        this.coverImage = coverImage;
        this.period = period;
        this.status = status;
        this.enrollmentPolicy = policy;
        this.enrollments = enrollments;
    }

    public final void enroll(Enrollment enrollment, Money payment) {
        validateStatus();
        enrollmentPolicy.validate(payment, enrollmentCount());
        enrollments.add(enrollment);
    }

    private void validateStatus() {
        if (!status.canEnroll()) {
            throw new IllegalStateException(String.format("모집중인 강의만 수강 신청이 가능합니다. (현재 상태: %s)", status));
        }
    }

    public int enrollmentCount() {
        return enrollments.count();
    }

    public SessionStatus getStatus() {
        return this.status;
    }

    public SessionType getType() {
        return enrollmentPolicy.getType();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return period.getStartDate();
    }

    public LocalDate getEndDate() {
        return period.getEndDate();
    }

    public EnrollmentPolicy getEnrollmentPolicy() {
        return enrollmentPolicy;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }
}
