package ru.andryss.weblab3.view.checkers.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("rFieldValidator")
public class RFieldValidator implements Validator {

    protected final String notSelectedErrorString = "field must be selected";
    protected final String moreThanOneSelectedErrorString = "select only one";
    protected final String isNotArrayOfNumbersErrorString = "object isn't an array of numbers";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (facesContext != null && uiComponent != null) {
            if (o == null) throw new ValidatorException(new FacesMessage(notSelectedErrorString));
            if (o instanceof Integer[]) {
                Integer[] converted = (Integer[]) o;
                if (converted.length != 1) {
                    throw new ValidatorException(new FacesMessage(moreThanOneSelectedErrorString));
                }
            } else {
                throw new ValidatorException(new FacesMessage(isNotArrayOfNumbersErrorString));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
