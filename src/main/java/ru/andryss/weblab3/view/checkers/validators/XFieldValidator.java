package ru.andryss.weblab3.view.checkers.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("xFieldValidator")
public class XFieldValidator implements Validator {

    protected final String blankErrorString = "field can't be blank";
    protected final String notInRangeErrorString = "x must be in range (-3...3)";
    protected final String isNotNumberErrorString = "object isn't a number";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (facesContext != null && uiComponent != null) {
            if (o == null) throw new ValidatorException(new FacesMessage(blankErrorString));
            try {
                int converted = o instanceof Number ? ((Number)o).intValue() : Integer.parseInt(o.toString());
                if (converted < -3 || converted > 3) {
                    throw new ValidatorException(new FacesMessage(notInRangeErrorString));
                }
            } catch (NumberFormatException e) {
                throw new ValidatorException(new FacesMessage(isNotNumberErrorString));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
