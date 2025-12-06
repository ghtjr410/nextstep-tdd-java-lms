package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question extends DeletableBaseEntity {
    private NsUser writer;

    private final Answers answers = new Answers();

    private QuestionContent content;

    public Question() {}

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        super(id);
        this.writer = writer;
        this.content = new QuestionContent(title, contents);
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        delete();
        answers.deleteAll();
    }

    public List<DeleteHistory> deleteHistories(LocalDateTime deletedAt) {
        List<DeleteHistory> histories = new ArrayList<>();
        histories.add(new DeleteHistory(ContentType.QUESTION, getId(), this.writer, deletedAt));
        histories.addAll(answers.deleteHistories(deletedAt));
        return histories;
    }

    private boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public NsUser getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "Question{" + super.toString()
                + ", writer="
                + writer + ", content="
                + content + ", deleted="
                + isDeleted() + '}';
    }
}
