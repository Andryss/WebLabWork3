package view.form;

import lombok.Setter;
import model.HistoryManager;
import model.Request;
import model.data.entities.History;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

@ManagedBean(name = "formBean")
@SessionScoped
public class FormBean {

    private Integer x;
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    private Double y;
    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }

    private Integer[] r;
    public Integer getR() {
        return r[0];
    }
    public Integer[] getRCheckbox() {
        return r;
    }
    public void setRCheckbox(Integer[] rCheckbox) {
        this.r = rCheckbox;
    }

    public String[] getRCheckBoxValues() {
        return new String[]{"1", "2", "3", "4", "5"};
    }


    @Setter
    @ManagedProperty("#{historyManager}")
    private HistoryManager historyManager;


    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public Set<History> getHistory() {
        return historyManager.getUserHistory(session);
    }

    public void processForm() {
        if (getX() != null && getY() != null && getR() != null) {
            processRequest();
            clearForm();
        }
    }

    private void processRequest() {
        Request request = new Request(getX(), getY(), getR());
        historyManager.addUserRequest(session, request);
    }

    private void clearForm() {
        setX(null);
        setY(null);
    }

    private static final DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    public String getResponseTimeString(long responseTime) {
        return formatter.format(responseTime);
    }
}
