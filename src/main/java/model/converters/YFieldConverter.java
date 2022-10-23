package model.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("yFieldConverter")
public class YFieldConverter extends DoubleConverter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (facesContext != null && uiComponent != null) {
            if (s == null) {
                return null;
            } else {
                s = s.trim();
                if (s.length() == 0) {
                    return null;
                } else {
                    try {
                        return Double.valueOf(s);
                    } catch (NumberFormatException e) {
                        throw new ConverterException(new FacesMessage("error number format"));
                    } catch (Exception e) {
                        throw new ConverterException(new FacesMessage("some error occur"));
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (facesContext != null && uiComponent != null) {
            if (o == null) {
                return "";
            } else if (o instanceof String) {
                return (String)o;
            } else {
                try {
                    return Double.toString(((Number) o).doubleValue());
                } catch (ClassCastException e) {
                    throw new ConverterException(new FacesMessage("object isn't a number"));
                } catch (Exception e) {
                    throw new ConverterException(new FacesMessage("some error occur"));
                }
            }
        } else {
            throw new NullPointerException();
        }
    }
}
