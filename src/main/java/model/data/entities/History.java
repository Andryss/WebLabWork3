package model.data.entities;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public History() {
    }

    public History(long responseTime, long executionTime, double x, double y, double r, boolean result) {
        this.responseTime = responseTime;
        this.executionTime = executionTime;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isResult() {
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(History o) {
        return - Long.compare(responseTime, o.responseTime);
    }
}
