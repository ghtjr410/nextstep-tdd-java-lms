package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> values;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> inputs) {
        this.values = inputs;
    }

    public void add(Answer answer) {
        this.values.add(answer);
    }

    public void deleteAll() {
        for (Answer answer : this.values) {
            answer.delete();
        }
    }

    public List<DeleteHistory> deleteHistories(LocalDateTime deletedAt) {
        List<DeleteHistory> histories = new ArrayList<>();
        for (Answer answer : this.values) {
            histories.add(answer.deleteHistory(deletedAt));
        }
        return histories;
    }

    public void validateDeletable(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : this.values) {
            if (!answer.isOwner(loginUser)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }
    }
}
