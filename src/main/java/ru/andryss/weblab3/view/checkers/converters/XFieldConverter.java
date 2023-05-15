package ru.andryss.weblab3.view.checkers.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.IntegerConverter;

@FacesConverter("xFieldConverter")
public class XFieldConverter extends IntegerConverter {

    protected final String notIntegerErrorString = "field must be integer";
    protected final String someErrorString = "some error occur";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (facesContext != null && uiComponent != null) {
            if (s == null) return null;
            s = s.trim();
            if (s.length() == 0) return null;
            try {
                return Integer.valueOf(s);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(notIntegerErrorString));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(someErrorString));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
