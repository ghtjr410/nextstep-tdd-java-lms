package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class Answer extends BaseEntity {
    private NsUser writer;

    private Question question;

    private String contents;

    private boolean deleted = false;

    public Answer() {}

    public Answer(NsUser writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, NsUser writer, Question question, String contents) {
        super(id);
        if (writer == null) {
            throw new UnAuthorizedException();
        }

        if (question == null) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public void delete() {
        this.deleted = true;
    }

    public DeleteHistory deleteHistory(LocalDateTime deletedDate) {
        return new DeleteHistory(ContentType.ANSWER, getId(), this.writer, deletedDate);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equals(writer);
    }

    public NsUser getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
    }
}
