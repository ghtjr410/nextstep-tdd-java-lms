package nextstep.courses.domain;

public class PaidSession extends Session {
    private final int capacity;
    private final Money price;

    public PaidSession(CoverImage coverImage, SessionPeriod period, int capacity, Money price) {
        super(coverImage, period);
        validateMaxEnrollment(capacity);
        this.capacity = capacity;
        this.price = price;
    }

    public PaidSession(CoverImage coverImage, SessionPeriod period, SessionStatus status, int capacity, Money price) {
        super(coverImage, period, status);
        validateMaxEnrollment(capacity);
        this.capacity = capacity;
        this.price = price;
    }

    private void validateMaxEnrollment(int maxEnrollment) {
        if (maxEnrollment <= 0) {
            throw new IllegalArgumentException(String.format("최대 수강 인원은 1명 이상이어야 합니다. (입력: %d)", maxEnrollment));
        }
    }

    public void enroll(Enrollment enrollment, Money payment) {
        validatePayment(payment);
        validateCapacity();
        super.enroll(enrollment);
    }

    private void validatePayment(Money payment) {
        if (!price.isSameAs(payment)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }

    private void validateCapacity() {
        if (enrollmentCount() >= capacity) {
            throw new IllegalStateException(String.format("수강 인원이 초과되었습니다. (최대: %d명)", capacity));
        }
    }
}
