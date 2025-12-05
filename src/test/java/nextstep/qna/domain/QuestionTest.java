package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void delete_성공() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        LocalDateTime now = LocalDateTime.now();

        assertThat(question.delete(NsUserTest.JAVAJIGI, now))
                .isEqualTo(new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter(), now));
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
}
