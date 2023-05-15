package ru.andryss.weblab3.view.checkers.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;
import java.util.regex.Pattern;

@FacesConverter("yFieldConverter")
public class YFieldConverter extends DoubleConverter {

    protected final Pattern xFieldPattern = Pattern.compile("^((-?[1-9]\\d*([.,]\\d+)?)|(0([.,]\\d+)?)|(-0[.,]\\d+))$");
    protected final String notDoubleErrorString = "field must be float";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (facesContext != null && uiComponent != null) {
            if (s == null) return null;
            s = s.trim();
            if (s.length() == 0) return null;
            if (xFieldPattern.matcher(s).find()) {
                return Double.parseDouble(s.replace(',', '.'));
            } else {
                throw new ConverterException(new FacesMessage(notDoubleErrorString));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
