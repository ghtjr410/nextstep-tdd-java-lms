package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionStatusTest {

    @Test
    void 준비중_수강신청불가() {
        assertThat(SessionStatus.PREPARING.canEnroll()).isFalse();
    }

    @Test
    void 모집중_수강신청가능() {
        assertThat(SessionStatus.RECRUITING.canEnroll()).isTrue();
    }

    @Test
    void 종료_수강신청불가() {
        assertThat(SessionStatus.CLOSED.canEnroll()).isFalse();
    }
}
