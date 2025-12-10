package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FreeEnrollmentPolicyTest {

    @Test
    void validate_무료강의_항상성공() {
        FreeEnrollmentPolicy policy = new FreeEnrollmentPolicy();

        assertThatCode(() -> policy.validate(null, 1000000000)).doesNotThrowAnyException();
    }

    @Test
    void getType_FREE_반환() {
        assertThat(new FreeEnrollmentPolicy().getType()).isEqualTo(SessionType.FREE);
    }
}
