package beans;

import model.HistoryManager;
import model.Request;
import model.data.entities.History;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

@ManagedBean(name = "formBean")
@SessionScoped
public class FormBean {

    private int x;
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    private double y;
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    private int r;
    public int getR() {
        return r;
    }
    public void setR(int r) {
        this.r = r;
    }


    private int[] rCheckbox;
    public int[] getRCheckbox() {
        return rCheckbox;
    }
    public void setRCheckbox(int[] rCheckbox) {
        this.rCheckbox = rCheckbox;
    }

    public int[] getRCheckBoxValues() {
        return new int[]{1, 2, 3, 4, 5};
    }


    private final String session = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

    public Set<History> getHistory() {
        return HistoryManager.instance.getUserHistory(session);
    }

    public void processForm() {
        if (rCheckbox != null && rCheckbox.length == 1) {
            r = rCheckbox[0];
            processRequest();
            clearForm();
        }
    }

    private void processRequest() {
        Request request = new Request(x, y, r);
        HistoryManager.instance.addUserRequest(session, request);
    }

    private void clearForm() {
        setX(0);
        setY(0);
        setRCheckbox(null);
    }

    private static final DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public String getResponseTimeString(long responseTime) {
        return formatter.format(responseTime);
    }
}
