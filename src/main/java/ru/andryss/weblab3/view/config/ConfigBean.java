package ru.andryss.weblab3.view.config;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Locale;

@ManagedBean(name = "configBean")
@SessionScoped
public class ConfigBean {

    private Locale locale = Locale.ENGLISH;
    public Locale getLocale() {
        return locale;
    }
    public String getLanguage() {
        return locale.getLanguage();
    }
    public void setLanguage(String language) {
        locale = new Locale(language);
    }

}
