package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnrollmentTest {

    @Test
    void 생성시_기본상태는_PENDING() {
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.PENDING);
    }
}
