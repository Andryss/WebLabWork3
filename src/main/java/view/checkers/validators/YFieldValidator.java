package view.checkers.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("yFieldValidator")
public class YFieldValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (facesContext != null && uiComponent != null) {
            if (o == null) throw new ValidatorException(new FacesMessage("field can't be blank"));
            try {
                double converted = o instanceof Number ? ((Number)o).doubleValue() : Double.parseDouble(o.toString());
                if (converted < -5 || converted > 5) {
                    throw new ValidatorException(new FacesMessage("y must be in range (-5...5)"));
                }
            } catch (NumberFormatException var6) {
                throw new ValidatorException(new FacesMessage("object isn't a number"));
            }
        } else {
            throw new NullPointerException();
        }
    }
}
