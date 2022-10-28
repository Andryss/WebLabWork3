package model.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("rFieldValidator")
public class RFieldValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (facesContext != null && uiComponent != null) {
            if (o == null) throw new ValidatorException(new FacesMessage("error empty"));
            if (o instanceof int[]) {
                int[] converted = (int[]) o;
                if (converted.length != 1) {
                    throw new ValidatorException(new FacesMessage("wrong selected count"));
                }
            } else {
                throw new ValidatorException(new FacesMessage("object isn't an array of numbers"));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
