package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question extends BaseEntity {
    private NsUser writer;

    private final Answers answers = new Answers();

    private QuestionContent content;

    private boolean deleted = false;

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

    public List<DeleteHistory> delete(NsUser loginUser, LocalDateTime now) throws CannotDeleteException {
        validateDeletable(loginUser);

        this.deleted = true;

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, getId(), this.writer, now));
        deleteHistories.addAll(answers.deleteAll(now));

        return deleteHistories;
    }

    public List<DeleteHistory> deleteHistories(LocalDateTime deletedAt) {
        List<DeleteHistory> histories = new ArrayList<>();
        histories.add(new DeleteHistory(ContentType.QUESTION, getId(), this.writer, deletedAt));
        histories.addAll(answers.deleteHistories(deletedAt));
        return histories;
    }

    private void validateDeletable(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        answers.validateDeletable(loginUser);
    }

    private boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public NsUser getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "Question{" + "content="
                + content + ", writer="
                + writer + ", answers="
                + answers + ", deleted="
                + deleted + '}';
    }
}
