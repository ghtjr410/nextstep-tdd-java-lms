package nextstep.courses.domain.session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Long save(Session session);

    void saveEnrollment(Enrollment enrollment);

    void updateEnrollment(Enrollment enrollment);

    Optional<Session> findById(Long id);

    List<Session> findByCourseId(Long courseId);
}
