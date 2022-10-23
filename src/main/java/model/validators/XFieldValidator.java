package model.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("xFieldValidator")
public class XFieldValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (facesContext != null && uiComponent != null) {
            if (o != null) {
                try {
                    long converted = o instanceof Number ? ((Number)o).longValue() : Long.parseLong(o.toString());
                    if (converted < -3 || converted > 3) {
                        throw new ValidatorException(new FacesMessage("error wrong range"));
                    }
                } catch (NumberFormatException var6) {
                    throw new ValidatorException(new FacesMessage("object isn't a number"));
                }
            }
        } else {
            throw new NullPointerException();
        }
    }
}
