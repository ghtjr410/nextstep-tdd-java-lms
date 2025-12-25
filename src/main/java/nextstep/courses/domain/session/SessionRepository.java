package nextstep.courses.domain.session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Long save(Session session, Long courseId);

    void saveEnrollment(Enrollment enrollment);

    Optional<Session> findById(Long id);

    List<Session> findByCourseId(Long courseId);
}
