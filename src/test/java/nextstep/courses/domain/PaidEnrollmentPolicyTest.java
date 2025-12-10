package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PaidEnrollmentPolicyTest {

    private PaidEnrollmentPolicy policy;

    @BeforeEach
    void setUp() {
        policy = new PaidEnrollmentPolicy(3, 50000);
    }

    @Test
    void validate_정상입력_성공() {
        assertThatCode(() -> policy.validate(new Money(50000), 0)).doesNotThrowAnyException();
    }

    @Test
    void validate_결제금액불일치_예외발생() {
        assertThatThrownBy(() -> policy.validate(new Money(30000), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("결제 금액이 수강료와 일치하지 않습니다");
    }

    @Test
    void validate_인원초과_예외발생() {
        assertThatThrownBy(() -> policy.validate(new Money(50000), 3))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("수강 인원이 초과");
    }

    @Test
    void getType_PAID반환() {
        assertThat(policy.getType()).isEqualTo(SessionType.PAID);
    }
}
