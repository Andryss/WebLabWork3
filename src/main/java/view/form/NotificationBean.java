package view.form;

import model.MissesManagerMXBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "notificationBean")
@SessionScoped
public class NotificationBean {

    @ManagedProperty("#{missesManager}")
    private MissesManagerMXBean missesManagerMXBean;
    public void setMissesManagerMXBean(MissesManagerMXBean missesManagerMXBean) {
        this.missesManagerMXBean = missesManagerMXBean;
    }

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public boolean hasMissesNotification() {
        return missesManagerMXBean.hasTwoMissesInARow(session);
    }
}
