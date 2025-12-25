package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.SessionType;

public class PaidEnrollmentPolicy extends EnrollmentPolicy {
    private final Capacity capacity;
    private final Money price;

    public PaidEnrollmentPolicy(int capacity, int price) {
        this(new Capacity(capacity), new Money(price));
    }

    public PaidEnrollmentPolicy(Capacity capacity, Money price) {
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public SessionType getType() {
        return SessionType.PAID;
    }

    @Override
    protected void validatePayment(Money payment) {
        if (!price.isSameAs(payment)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }

    @Override
    protected void validateCapacity(int currentCount) {
        if (capacity.isOver(currentCount)) {
            throw new IllegalStateException(String.format("수강 인원이 초과되었습니다. (최대: %d명)", capacity.getValue()));
        }
    }
}
