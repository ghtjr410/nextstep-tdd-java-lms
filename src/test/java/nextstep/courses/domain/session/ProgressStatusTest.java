package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProgressStatusTest {

    @ParameterizedTest
    @CsvSource({"PREPARING, true", "IN_PROGRESS, true", "CLOSED, false"})
    void canEnroll_진행상태별_수강신청_가능_여부(ProgressStatus status, boolean expected) {
        assertThat(status.canEnroll()).isEqualTo(expected);
    }
}
