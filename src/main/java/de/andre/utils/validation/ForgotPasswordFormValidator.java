package de.andre.utils.validation;

import de.andre.entity.core.utils.ForgotPasswordForm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static de.andre.utils.HybrisConstants.MASK_PASSWORD;

/**
 * Created by andreika on 4/13/2015.
 */

@Component
public class ForgotPasswordFormValidator implements Validator{
	@Override
	public boolean supports(final Class<?> clazz) {
		return ForgotPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		ForgotPasswordForm passwordForm = (ForgotPasswordForm) target;
		final String enteredPassword = passwordForm.getEnteredPassword();
		final String confirmedPassword = passwordForm.getConfirmedPassword();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enteredPassword", "password.entered.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "password.confirmed.empty");

		if (!MASK_PASSWORD.matcher(enteredPassword).matches()) {
			errors.reject("enteredPassword", "passwords.notValid");
		}

		if (StringUtils.hasText(enteredPassword) && StringUtils.hasText(confirmedPassword) && !confirmedPassword.equals(enteredPassword)) {
			errors.reject("enteredPassword", "passwords.notEquals");
		}
	}
}
