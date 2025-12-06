package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
