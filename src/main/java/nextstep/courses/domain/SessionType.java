package nextstep.courses.domain;

public enum SessionType {
    FREE("무료"),
    PAID("유료");

    SessionType(String description) {
        this.description = description;
    }

    private final String description;
}
