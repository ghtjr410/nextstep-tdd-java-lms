package nextstep.courses.domain.session;

public enum SessionType {
    FREE("무료"),
    PAID("유료");

    SessionType(String description) {
        this.description = description;
    }

    private final String description;
}
