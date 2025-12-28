package nextstep.courses.domain.session;

import java.time.LocalDate;
import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.image.CoverImages;
import nextstep.courses.domain.session.policy.EnrollmentPolicy;

public class Session {
    private final Long id;
    private final Long courseId;
    private final CoverImages coverImages;
    private final SessionPeriod period;
    private final ProgressStatus progressStatus;
    private final RecruitmentStatus recruitmentStatus;
    private final EnrollmentPolicy enrollmentPolicy;
    private final Enrollments enrollments;

    public Session(Long courseId, CoverImages coverImages, SessionPeriod period, EnrollmentPolicy policy) {
        this(
                0L,
                courseId,
                coverImages,
                period,
                ProgressStatus.PREPARING,
                RecruitmentStatus.NOT_RECRUITING,
                policy,
                new Enrollments());
    }

    public Session(
            Long courseId,
            CoverImages coverImages,
            SessionPeriod period,
            ProgressStatus progressStatus,
            RecruitmentStatus recruitmentStatus,
            EnrollmentPolicy policy) {
        this(0L, courseId, coverImages, period, progressStatus, recruitmentStatus, policy, new Enrollments());
    }

    public Session(
            Long id,
            Long courseId,
            CoverImages coverImages,
            SessionPeriod period,
            ProgressStatus progressStatus,
            RecruitmentStatus recruitmentStatus,
            EnrollmentPolicy policy,
            Enrollments enrollments) {
        this.id = id;
        this.courseId = courseId;
        this.coverImages = coverImages;
        this.period = period;
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

    public Enrollment approve(Long studentId) {
        return enrollments.approve(studentId);
    }

    public Enrollment reject(Long studentId) {
        return enrollments.reject(studentId);
    }

    public int enrollmentCount() {
        return enrollments.count();
    }

    public Long getCourseId() {
        return courseId;
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

    public CoverImages getCoverImages() {
        return coverImages;
    }

    public Enrollments getEnrollments() {
        return enrollments;
    }
}
