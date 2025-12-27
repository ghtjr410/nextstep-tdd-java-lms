package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnrollmentTest {

    @Test
    void 생성자_생성시_기본상태는_PENDING() {
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.PENDING);
    }

    @Test
    void approve_승인하면_APPROVED() {
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        enrollment.approve();

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.APPROVED);
    }

    @Test
    void reject_취소하면_REJECTED() {
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        enrollment.reject();

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.REJECTED);
    }
}
