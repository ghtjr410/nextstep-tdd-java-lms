package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecruitmentStatusTest {

    @Test
    void canEnroll_모집중일때_수강신청_가능() {
        assertThat(RecruitmentStatus.RECRUITING.canEnroll()).isTrue();
    }

    @Test
    void canEnroll_비모집중일때_수강신청_불가() {
        assertThat(RecruitmentStatus.NOT_RECRUITING.canEnroll()).isFalse();
    }
}
