package view.form;

import model.CountManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "statisticsBean")
@SessionScoped
public class StatisticsBean {

    @ManagedProperty("#{countManager}")
    private CountManager countManager;
    public void setCountManager(CountManager countManager) {
        this.countManager = countManager;
    }

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public long getAllCount() {
        return countManager.getAllCount(session);
    }

    public long getMissesCount() {
        return countManager.getMissedCount(session);
    }

    public long getHitCount() {
        return countManager.getHitCount(session);
    }

    public double getMissesPercentage() {
        return countManager.getMissesPercentage(session);
    }

    public double getHitPercentage() {
        return countManager.getHitPercentage(session);
    }

    public String toPercentString(double value) {
        return String.format("%.2f", value);
    }
}
