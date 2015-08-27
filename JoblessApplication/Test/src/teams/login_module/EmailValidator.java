package teams.login_module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validEmail")
public class EmailValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validates the e-mail input
	 * 
	 * @param context FacesContext
	 * @param component UIComponent
	 * @param value Object
	 * @author Galina_Petrova
	 * @author Slavka_Peleva
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());
		if (value.toString().isEmpty()) {
			FacesMessage msg2 = new FacesMessage("E-mail can't be empty.", "Required field!");
			msg2.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg2);

		}
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("E-mail validation failed.", "Invalid E-mail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}
}