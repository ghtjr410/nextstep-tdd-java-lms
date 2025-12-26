package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.SessionType;

public abstract class EnrollmentPolicy {
    public final void validate(Money payment, int currentCount) {
        validatePayment(payment);
        validateCapacity(currentCount);
    }

    public abstract SessionType getType();

    protected abstract void validatePayment(Money payment);

    protected abstract void validateCapacity(int currentCount);
}
