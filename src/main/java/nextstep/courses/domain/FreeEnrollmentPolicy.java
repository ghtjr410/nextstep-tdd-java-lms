package nextstep.courses.domain;

public class FreeEnrollmentPolicy extends EnrollmentPolicy {

    @Override
    public SessionType getType() {
        return SessionType.FREE;
    }

    @Override
    protected void validatePayment(Money payment) {
        // 무료는 검증 X
    }

    @Override
    protected void validateCapacity(int currentCount) {
        // 무료는 검증 X
    }
}
