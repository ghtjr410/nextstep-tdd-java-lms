package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FreeSessionTest {

    @Test
    void 생성자_정상입력_생성성공() {
        CoverImage coverImage = new CoverImage("image.png", 1024, 300, 200);
        SessionPeriod sessionPeriod = new SessionPeriod(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31));

        assertThatCode(() -> new FreeSession(coverImage, sessionPeriod)).doesNotThrowAnyException();
    }
}
