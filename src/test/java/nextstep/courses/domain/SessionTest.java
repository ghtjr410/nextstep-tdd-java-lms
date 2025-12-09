package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionTest {

    @Test
    void 생성자_정상입력_생성성공() {
        CoverImage coverImage = new CoverImage("image.png", 1024, 300, 200);
        SessionPeriod period = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));

        Session session = new Session(coverImage, period);

        assertThat(session).isNotNull();
        assertThat(session.getStatus()).isEqualTo(SessionStatus.PREPARING);
    }
}
