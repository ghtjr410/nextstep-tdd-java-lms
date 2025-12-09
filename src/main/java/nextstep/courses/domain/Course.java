package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Course {
    private Long id;

    private String title;

    private int generation;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Course() {}

    public Course(String title, int generation, Long creatorId, LocalDateTime now) {
        this(0L, title, generation, creatorId, now, null);
    }

    public Course(String title, int generation, Long creatorId) {
        this(0L, title, generation, creatorId, LocalDateTime.now(), null);
    }

    public Course(
            Long id, String title, int generation, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.generation = generation;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public int getGeneration() {
        return generation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Course{" + "id="
                + id + ", title='"
                + title + '\'' + ", creatorId="
                + creatorId + ", createdAt="
                + createdAt + ", updatedAt="
                + updatedAt + '}';
    }
}
