package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnrollmentsTest {

    @Test
    void add_정상입력_성공() {
        var e1 = new Enrollment(1L, 1L, LocalDateTime.now());
        var e2 = new Enrollment(1L, 2L, LocalDateTime.now());
        var enrollments = new Enrollments();

        assertThatCode(() -> {
                    enrollments.add(e1);
                    enrollments.add(e2);
                })
                .doesNotThrowAnyException();
    }

    @Test
    void add_중복신청_예외발생() {
        var e1 = new Enrollment(1L, 1L, LocalDateTime.now());
        var e2 = new Enrollment(1L, 1L, LocalDateTime.now());

        var enrollments = new Enrollments();

        assertThatThrownBy(() -> {
                    enrollments.add(e1);
                    enrollments.add(e2);
                })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 수강 신청한 강의입니다.");
    }

    @Test
    void approve_없는학생승인시_예외발생() {
        Enrollments enrollments = new Enrollments();

        assertThatThrownBy(() -> enrollments.approve(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수강 신청 내역이 없습니다");
    }

    @Test
    void reject_없는학생취소시_예외발생() {
        Enrollments enrollments = new Enrollments();

        assertThatThrownBy(() -> enrollments.reject(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수강 신청 내역이 없습니다");
    }
}
