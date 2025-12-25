package nextstep.courses.domain.session;

public enum SessionStatus {
    PREPARING("준비중"),
    RECRUITING("모집중"),
    CLOSED("종료");

    SessionStatus(String description) {
        this.description = description;
    }

    private final String description;

    public boolean canEnroll() {
        return this == RECRUITING;
    }
}
