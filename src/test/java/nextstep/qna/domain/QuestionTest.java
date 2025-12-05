package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void delete_성공() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");
        question.addAnswer(answer);
        LocalDateTime now = LocalDateTime.now();

        assertThat(question.delete(NsUserTest.JAVAJIGI, now))
                .isEqualTo(List.of(
                        new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter(), now),
                        new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), now)));
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    void delete_소유자불일치_예외발생() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> question.delete(NsUserTest.SANJIGI, now))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void delete_다른사람답변_예외발생() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.SANJIGI, question, "Answers Contents1");
        question.addAnswer(answer);
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> question.delete(NsUserTest.JAVAJIGI, now))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
