package teams.login_module;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {



	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	     String password = (String) value;
	        String confirm = (String) component.getAttributes().get("confirm");

	        if (password == null || confirm == null) {
	            FacesMessage msg1 = new FacesMessage("Password null", "Please enter password");
	            msg1.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg1);
	        }

	        if (!password.equals(confirm)) {
	        	FacesMessage msg = new FacesMessage("Password missmatch", "Passwords don't match");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg);
	        }
	        if (password.length() < 5) {
	        	FacesMessage msg5 = new FacesMessage("Password short", "Password is too short");
	            msg5.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg5);
			}
	        if (password.contains(" ")) {
	        	FacesMessage msg5 = new FacesMessage("Password space", "Invalid format.");
	            msg5.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg5);
			}
		
	}

}