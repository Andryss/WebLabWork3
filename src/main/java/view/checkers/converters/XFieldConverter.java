package view.checkers.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.IntegerConverter;

@FacesConverter("xFieldConverter")
public class XFieldConverter extends IntegerConverter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (facesContext != null && uiComponent != null) {
            if (s == null) return null;
            s = s.trim();
            if (s.length() == 0) return null;
            try {
                return Integer.valueOf(s);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage("field must be integer"));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage("some error occur"));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
