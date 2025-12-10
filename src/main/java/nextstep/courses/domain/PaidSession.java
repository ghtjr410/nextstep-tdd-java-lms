package nextstep.courses.domain;

public class PaidSession extends Session {
    private final int maxEnrollment;
    private final Money fee;

    public PaidSession(CoverImage coverImage, SessionPeriod period, int maxEnrollment, Money fee) {
        super(coverImage, period);
        validateMaxEnrollment(maxEnrollment);
        this.maxEnrollment = maxEnrollment;
        this.fee = fee;
    }

    public PaidSession(
            CoverImage coverImage, SessionPeriod period, SessionStatus status, int maxEnrollment, Money fee) {
        super(coverImage, period, status);
        this.maxEnrollment = maxEnrollment;
        this.fee = fee;
    }

    private void validateMaxEnrollment(int maxEnrollment) {
        if (maxEnrollment <= 0) {
            throw new IllegalArgumentException(String.format("최대 수강 인원은 1명 이상이어야 합니다. (입력: %d)", maxEnrollment));
        }
    }

    public void enroll(Enrollment enrollment, Money payment) {
        validatePayment(payment);
        super.enroll(enrollment);
    }

    private void validatePayment(Money payment) {
        if (!fee.isSameAs(payment)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }
}
