package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AnswersTest {

    @Test
    void validateDeletable_소유자일치() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answers answers = new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1")));

        assertThatCode(() -> answers.validateDeletable(NsUserTest.JAVAJIGI)).doesNotThrowAnyException();
    }

    @Test
    void validateDeletable_소유자불일치_예외발생() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answers answers = new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1")));

        assertThatThrownBy(() -> answers.validateDeletable(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
