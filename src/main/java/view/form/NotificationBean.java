package view.form;

import lombok.Setter;
import model.MissesManagerMXBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "notificationBean")
@SessionScoped
public class NotificationBean {

    @Setter
    @ManagedProperty("#{missesManager}")
    private MissesManagerMXBean missesManagerMXBean;

    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public boolean hasMissesNotification() {
        return missesManagerMXBean.hasTwoMissesInARow(session);
    }
}
