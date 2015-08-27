package teams.login_module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validName")
public class NameValidator implements Validator {

	private static final String NAME_PATTERN = "[A-Z]{1}[a-z]+";
	private Pattern namePattern;
	private Matcher matcher;

	public NameValidator() {
		
		namePattern = Pattern.compile(NAME_PATTERN);
	}

	/**
	 * Validates the inputs for first and last name
	 * @param context FacesContext
	 * @param component UIComponent
	 * @param value Object
	 * @author Galina_Petrova
	 * @author Zahra_Harira
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		matcher = namePattern.matcher(value.toString());
		if (value.toString().isEmpty()) {
			FacesMessage msg2 = new FacesMessage("Name error.", "Required field!");
			msg2.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg2);
		}
		
		if (!matcher.matches()) {

			FacesMessage msg = new FacesMessage("Name validation failed.", "Invalid name format!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}
}