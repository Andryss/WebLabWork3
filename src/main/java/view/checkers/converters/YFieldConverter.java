package view.checkers.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;
import java.util.regex.Pattern;

@FacesConverter("yFieldConverter")
public class YFieldConverter extends DoubleConverter {

    private static final Pattern xFieldPattern = Pattern.compile("^((-?[1-9]\\d*([.,]\\d+)?)|(0([.,]\\d+)?)|(-0[.,]\\d+))$");

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (facesContext != null && uiComponent != null) {
            if (s == null) return null;
            s = s.trim();
            if (s.length() == 0) return null;
            if (xFieldPattern.matcher(s).find()) {
                return Double.parseDouble(s.replace(',', '.'));
            } else {
                throw new ConverterException(new FacesMessage("field must be float"));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
