package nextstep.courses.domain;

public class PaidSession extends Session {
    private final Capacity capacity;
    private final Money price;

    public PaidSession(CoverImage coverImage, SessionPeriod period, int capacity, int price) {
        this(coverImage, period, SessionStatus.PREPARING, new Capacity(capacity), new Money(price));
    }

    public PaidSession(CoverImage coverImage, SessionPeriod period, SessionStatus status, int capacity, int price) {
        this(coverImage, period, status, new Capacity(capacity), new Money(price));
    }

    public PaidSession(
            CoverImage coverImage, SessionPeriod period, SessionStatus status, Capacity capacity, Money price) {
        super(coverImage, period, status);
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    protected void validatePaymentPolicy(Money payment) {
        if (!price.isSameAs(payment)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }

    @Override
    protected void validateCapacityPolicy() {
        if (this.capacity.isOver(enrollmentCount())) {
            throw new IllegalStateException(String.format("수강 인원이 초과되었습니다. (최대: %d명)", capacity.getValue()));
        }
    }
}
