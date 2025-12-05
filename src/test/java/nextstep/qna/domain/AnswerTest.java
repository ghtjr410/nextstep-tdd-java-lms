package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void delete() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        LocalDateTime now = LocalDateTime.now();

        assertThat(answer.delete(now))
                .isEqualTo(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), now));
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    void deleteHistory() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 10, 0);

        DeleteHistory history = answer.deleteHistory(fixedTime);

        assertThat(history)
                .isEqualTo(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), fixedTime));
    }
}
