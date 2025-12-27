package nextstep.courses.domain.session;

import java.time.LocalDate;
import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.policy.EnrollmentPolicy;

public class Session {
    private final Long id;
    private final Long courseId;
    private final CoverImage coverImage;
    private final SessionPeriod period;
    private final SessionStatus status;
    private final ProgressStatus progressStatus;
    private final RecruitmentStatus recruitmentStatus;
    private final EnrollmentPolicy enrollmentPolicy;
    private final Enrollments enrollments;

    public Session(Long courseId, CoverImage coverImage, SessionPeriod period, EnrollmentPolicy policy) {
        this(0L, courseId, coverImage, period, SessionStatus.PREPARING, null, null, policy, new Enrollments());
    }

    public Session(
            Long courseId, CoverImage coverImage, SessionPeriod period, SessionStatus status, EnrollmentPolicy policy) {
        this(
                0L,
                courseId,
                coverImage,
                period,
                status,
                toProgressStatus(status),
                toRecruitmentStatus(status),
                policy,
                new Enrollments());
    }

    public Session(
            Long courseId,
            CoverImage coverImage,
            SessionPeriod period,
            SessionStatus status,
            ProgressStatus progressStatus,
            RecruitmentStatus recruitmentStatus,
            EnrollmentPolicy policy) {
        this(0L, courseId, coverImage, period, status, progressStatus, recruitmentStatus, policy, new Enrollments());
    }

    private static ProgressStatus toProgressStatus(SessionStatus status) {
        switch (status) {
            case RECRUITING:
                return ProgressStatus.IN_PROGRESS;
            case CLOSED:
                return ProgressStatus.CLOSED;
            case PREPARING:
            default:
                return ProgressStatus.PREPARING;
        }
    }

    private static RecruitmentStatus toRecruitmentStatus(SessionStatus status) {
        if (status == SessionStatus.RECRUITING) {
            return RecruitmentStatus.RECRUITING;
        }
        return RecruitmentStatus.NOT_RECRUITING;
    }

    public Session(
            Long id,
            Long courseId,
            CoverImage coverImage,
            SessionPeriod period,
            SessionStatus status,
            ProgressStatus progressStatus,
            RecruitmentStatus recruitmentStatus,
            EnrollmentPolicy policy,
            Enrollments enrollments) {
        this.id = id;
        this.courseId = courseId;
        this.coverImage = coverImage;
        this.period = period;
        this.status = status;
        this.progressStatus = progressStatus;
        this.recruitmentStatus = recruitmentStatus;
        this.enrollmentPolicy = policy;
        this.enrollments = enrollments;
    }

    public final void enroll(Enrollment enrollment, Money payment) {
        validateStatus();
        enrollmentPolicy.validate(payment, enrollmentCount());
        enrollments.add(enrollment);
    }

    private void validateStatus() {
        if (!progressStatus.canEnroll()) {
            throw new IllegalStateException(String.format("종료된 강의는 수강 신청이 불가능합니다. (현재 상태: %s)", progressStatus));
        }
        if (!recruitmentStatus.canEnroll()) {
            throw new IllegalStateException(String.format("모집중인 강의만 수강 신청이 가능합니다. (현재 상태: %s)", recruitmentStatus));
        }
    }

    public int enrollmentCount() {
        return enrollments.count();
    }

    public Long getCourseId() {
        return courseId;
    }

    public SessionStatus getStatus() {
        return this.status;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public RecruitmentStatus getRecruitmentStatus() {
        return recruitmentStatus;
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
