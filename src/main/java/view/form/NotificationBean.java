package view.form;

import model.CountManager;
import model.MissesManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "notificationBean")
@SessionScoped
public class NotificationBean {

    @ManagedProperty("#{missesManager}")
    private MissesManager missesManager;
    public void setMissesManager(MissesManager missesManager) {
        this.missesManager = missesManager;
    }

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public boolean hasMissesNotification() {
        return missesManager.hasTwoMissesInARow(session);
    }
}
