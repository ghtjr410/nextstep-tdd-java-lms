package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecruitmentStatusTest {

    @ParameterizedTest
    @CsvSource({"RECRUITING, true", "NOT_RECRUITING, false"})
    void canEnroll_모집상태별_수강신청_가능_여부(RecruitmentStatus status, boolean expected) {
        assertThat(status.canEnroll()).isEqualTo(expected);
    }
}
