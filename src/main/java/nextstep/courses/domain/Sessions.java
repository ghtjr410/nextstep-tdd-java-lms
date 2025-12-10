package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private final List<Session> values;

    public Sessions() {
        this(new ArrayList<>());
    }

    public Sessions(List<Session> values) {
        this.values = new ArrayList<>(values);
    }

    public void add(Session session) {
        this.values.add(session);
    }

    public int count() {
        return this.values.size();
    }
}
