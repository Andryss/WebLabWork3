package model.data.entities;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_session_id", unique = true, nullable = false)
    private String sessionId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OrderBy(value = "responseTime ASC")
    private SortedSet<History> histories;

    public User() {
    }

    public User(String sessionId) {
        this.sessionId = sessionId;
        histories = new TreeSet<>();
    }

    public long getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public SortedSet<History> getHistories() {
        return histories;
    }
}
