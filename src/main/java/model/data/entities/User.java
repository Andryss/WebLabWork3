package model.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_session_id", unique = true, nullable = false)
    private String sessionId;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OrderBy(value = "responseTime ASC")
    private SortedSet<History> histories;

    public User(String sessionId) {
        this.sessionId = sessionId;
        histories = new TreeSet<>();
    }
}
