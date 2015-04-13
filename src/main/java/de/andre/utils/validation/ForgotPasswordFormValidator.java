package de.andre.utils.validation;

import de.andre.entity.core.utils.ForgotPasswordForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by andreika on 4/13/2015.
 */
public class ForgotPasswordFormValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return ForgotPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ForgotPasswordForm passwordForm = (ForgotPasswordForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enteredPassword", "password 1 empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "password 2 empty");

		if (!passwordForm.getConfirmedPassword().equals(passwordForm.getEnteredPassword())) {
			errors.reject("passwords not equals");
		}
	}
}
