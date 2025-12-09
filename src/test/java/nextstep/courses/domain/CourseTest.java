package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CourseTest {

    @Test
    void 생성자_정상입력_생성성공() {
        LocalDateTime fixedNow = LocalDateTime.now();
        assertThatCode(() -> new Course("과정명", 1, 1L, fixedNow)).doesNotThrowAnyException();
    }
}
