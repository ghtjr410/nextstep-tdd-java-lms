package nextstep.courses.service;

import java.time.LocalDateTime;
import nextstep.courses.domain.Money;
import nextstep.courses.domain.session.Enrollment;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void enroll(Long sessionId, Long studentId, Payment payment) {
        Session session =
                sessionRepository.findById(sessionId).orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));

        Enrollment enrollment = new Enrollment(sessionId, studentId, LocalDateTime.now());
        session.enroll(enrollment, new Money(payment.getAmount()));

        sessionRepository.saveEnrollment(enrollment);
    }
}
