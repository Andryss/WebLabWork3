package view.form;

import model.CountManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "notificationBean")
@SessionScoped
public class NotificationBean {

    @ManagedProperty("#{countManager}")
    private CountManager countManager;
    public void setCountManager(CountManager countManager) {
        this.countManager = countManager;
    }

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public boolean hasMissesNotification() {
        return countManager.hasTwoMissesInARow(session);
    }
}
