package nextstep.courses.domain;

public class Money {
    private final int amount;

    public Money(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private static void validate(int value) {
        if (value < 0) throw new IllegalArgumentException(String.format("금액은 0 이상이어야 합니다. (입력: %d)", value));
    }

    public boolean isSameAs(Money other) {
        return this.amount == other.amount;
    }
}
