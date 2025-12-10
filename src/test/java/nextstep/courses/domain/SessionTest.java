package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionTest {
    private CoverImage coverImage;
    private SessionPeriod period;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage("image.png", 1024, 300, 200);
        period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));
    }

    @Test
    void 생성자_정상입력_준비중상태() {
        Session session = new FreeSession(coverImage, period);

        assertThat(session.getStatus()).isEqualTo(SessionStatus.PREPARING);
    }

    @Test
    void enroll_준비중_예외발생() {
        Session session = new FreeSession(coverImage, period);
        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThatThrownBy(() -> session.enroll(enrollment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("모집중인 강의만");
    }
}
