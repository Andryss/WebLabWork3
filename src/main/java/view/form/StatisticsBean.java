package view.form;

import model.CountManagerMXBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "statisticsBean")
@SessionScoped
public class StatisticsBean {

    @ManagedProperty("#{countManager}")
    private CountManagerMXBean countManagerMXBean;
    public void setCountManagerMXBean(CountManagerMXBean countManagerMXBean) {
        this.countManagerMXBean = countManagerMXBean;
    }

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public long getAllCount() {
        return countManagerMXBean.getAllCount(session);
    }

    public long getMissesCount() {
        return countManagerMXBean.getMissedCount(session);
    }

    public long getHitCount() {
        return countManagerMXBean.getHitCount(session);
    }

    public double getMissesPercentage() {
        long allCount = getAllCount();
        if (allCount == 0) return 0.0;
        return (double) getMissesCount() / allCount;
    }

    public double getHitPercentage() {
        long allCount = getAllCount();
        if (allCount == 0) return 0.0;
        return (double) getHitCount() / allCount;
    }

    public String toPercentString(double value) {
        return String.format("%.2f", value);
    }
}
