package de.andre.utils.validation;

import de.andre.entity.core.utils.ForgotPasswordForm;
import org.springframework.stereotype.Component;
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enteredPassword", "password.entered.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "password.confirmed.empty");

		if (!MASK_PASSWORD.matcher(passwordForm.getEnteredPassword()).matches()) {
			errors.reject("enteredPassword", "passwords.notValid");
		}

		if (!passwordForm.getConfirmedPassword().equals(passwordForm.getEnteredPassword())) {
			errors.reject("enteredPassword", "passwords.notEquals");
		}
	}
}
