package nextstep.courses.domain;

public class Session {
    private Long id;
    private CoverImage coverImage;
    private SessionPeriod period;
    private SessionStatus status;

    public Session(CoverImage coverImage, SessionPeriod period) {
        this(0L, coverImage, period, SessionStatus.PREPARING);
    }

    public Session(Long id, CoverImage coverImage, SessionPeriod period, SessionStatus status) {
        this.id = id;
        this.coverImage = coverImage;
        this.period = period;
        this.status = status;
    }

    public boolean canEnroll() {
        return this.status.canEnroll();
    }

    public SessionStatus getStatus() {
        return this.status;
    }
}
