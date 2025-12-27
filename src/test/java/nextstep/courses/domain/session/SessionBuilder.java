package nextstep.courses.domain.session;

import java.time.LocalDate;
import nextstep.courses.domain.session.image.CoverImage;
import nextstep.courses.domain.session.policy.EnrollmentPolicy;
import nextstep.courses.domain.session.policy.FreeEnrollmentPolicy;
import nextstep.courses.domain.session.policy.PaidEnrollmentPolicy;

public class SessionBuilder {
    private Long courseId = 1L;
    private CoverImage coverImage = null;
    private SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
    private ProgressStatus progressStatus = ProgressStatus.PREPARING;
    private RecruitmentStatus recruitmentStatus = RecruitmentStatus.NOT_RECRUITING;
    private EnrollmentPolicy policy = new FreeEnrollmentPolicy();

    public static SessionBuilder aSession() {
        return new SessionBuilder();
    }

    public SessionBuilder withCourseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }

    public SessionBuilder withCoverImage(String filename, long fileSize, int width, int height) {
        this.coverImage = new CoverImage(filename, fileSize, width, height);
        return this;
    }

    public SessionBuilder withProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
        return this;
    }

    public SessionBuilder withRecruitmentStatus(RecruitmentStatus recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
        return this;
    }

    public SessionBuilder withFreePolicy() {
        this.policy = new FreeEnrollmentPolicy();
        return this;
    }

    public SessionBuilder withPaidPolicy(int maxEnrollment, int price) {
        this.policy = new PaidEnrollmentPolicy(maxEnrollment, price);
        return this;
    }

    public Session build() {
        return new Session(courseId, coverImage, period, progressStatus, recruitmentStatus, policy);
    }
}
