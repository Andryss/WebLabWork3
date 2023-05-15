package model.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor

@Entity
@Table(name = "histories")
public class History implements Comparable<History> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "response_time", nullable = false)
    private long responseTime;

    @Column(name = "execution_time", nullable = false)
    private long executionTime;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Column(name = "r", nullable = false)
    private double r;

    @Column(name = "result", nullable = false)
    private boolean result;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public History(long responseTime, long executionTime, double x, double y, double r, boolean result) {
        this.responseTime = responseTime;
        this.executionTime = executionTime;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    @Override
    public int compareTo(History o) {
        return - Long.compare(responseTime, o.responseTime);
    }
}
