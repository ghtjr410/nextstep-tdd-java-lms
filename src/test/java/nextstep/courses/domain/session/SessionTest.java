package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import nextstep.courses.domain.Money;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionTest {

    @Test
    void 생성자_기본_생성시_준비중_비모집중_상태() {
        Session session = SessionBuilder.aSession().build();

        assertThat(session.getProgressStatus()).isEqualTo(ProgressStatus.PREPARING);
        assertThat(session.getRecruitmentStatus()).isEqualTo(RecruitmentStatus.NOT_RECRUITING);
    }

    @ParameterizedTest
    @MethodSource("수강신청_가능_상태")
    void 수강신청_가능_조합(ProgressStatus progressStatus, RecruitmentStatus recruitmentStatus) {
        Session session = SessionBuilder.aSession()
                .withProgressStatus(progressStatus)
                .withRecruitmentStatus(recruitmentStatus)
                .withPaidPolicy(30, 50000)
                .build();

        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThatCode(() -> session.enroll(enrollment, new Money(50000))).doesNotThrowAnyException();
    }

    static Stream<Arguments> 수강신청_가능_상태() {
        return Stream.of(
                Arguments.of(ProgressStatus.PREPARING, RecruitmentStatus.RECRUITING),
                Arguments.of(ProgressStatus.IN_PROGRESS, RecruitmentStatus.RECRUITING));
    }

    @Test
    void enroll_종료된_강의는_수강신청_불가() {
        Session session = SessionBuilder.aSession()
                .withProgressStatus(ProgressStatus.CLOSED)
                .withRecruitmentStatus(RecruitmentStatus.RECRUITING)
                .withPaidPolicy(30, 50000)
                .build();

        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThatThrownBy(() -> session.enroll(enrollment, new Money(50000)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("종료된 강의");
    }

    @Test
    void enroll_비모집중이면_수강신청_불가() {
        Session session = SessionBuilder.aSession()
                .withProgressStatus(ProgressStatus.IN_PROGRESS)
                .withRecruitmentStatus(RecruitmentStatus.NOT_RECRUITING)
                .withPaidPolicy(30, 50000)
                .build();

        Enrollment enrollment = new Enrollment(1L, 1L, LocalDateTime.now());

        assertThatThrownBy(() -> session.enroll(enrollment, new Money(50000)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("모집중인 강의만");
    }
}
