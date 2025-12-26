package nextstep.courses.domain.session.policy;

public class Capacity {
    private final int value;

    public Capacity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(String.format("최대 수강 인원은 1명 이상이어야 합니다. (입력: %d)", value));
        }
        this.value = value;
    }

    public boolean isOver(int currentCount) {
        return currentCount >= value;
    }

    public int getValue() {
        return this.value;
    }
}
