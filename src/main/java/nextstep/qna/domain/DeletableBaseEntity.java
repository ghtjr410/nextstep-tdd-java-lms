package nextstep.qna.domain;

import java.time.LocalDateTime;

public abstract class DeletableBaseEntity {
    private Long id;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;
    private boolean deleted = false;

    public DeletableBaseEntity() {}

    public DeletableBaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    protected void delete() {
        this.deleted = true;
    }

    @Override
    public String toString() {
        return "id=" + id + ", deleted=" + deleted;
    }
}
