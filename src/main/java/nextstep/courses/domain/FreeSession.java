package nextstep.courses.domain;

public class FreeSession extends Session {
    public FreeSession(CoverImage coverImage, SessionPeriod period) {
        super(coverImage, period);
    }

    public FreeSession(CoverImage coverImage, SessionPeriod period, SessionStatus status) {
        super(coverImage, period, status);
    }

    @Override
    protected void validatePaymentPolicy(Money payment) {}

    @Override
    protected void validateCapacityPolicy() {}
}
