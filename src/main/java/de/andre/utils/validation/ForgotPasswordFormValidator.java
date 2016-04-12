package de.andre.utils.validation;

import de.andre.entity.util.ForgotPasswordForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static de.andre.utils.HybrisConstants.MASK_PASSWORD;

public class ForgotPasswordFormValidator implements Validator {
    @Override
    public boolean supports(final Class<?> clazz) {
        return ForgotPasswordForm.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final ForgotPasswordForm passwordForm = (ForgotPasswordForm) target;
        final String enteredPassword = passwordForm.getEnteredPassword();
        final String confirmedPassword = passwordForm.getConfirmedPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enteredPassword", "password.entered.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "password.confirmed.empty");

        if (errors.getFieldError("enteredPassword") == null &&
                !MASK_PASSWORD.matcher(enteredPassword).matches()) {
            errors.reject("enteredPassword", "passwords.notValid");
        }

        if (errors.hasErrors()) {
            return;
        }

        if (!confirmedPassword.equals(enteredPassword)) {
            errors.reject("enteredPassword", "passwords.notEquals");
        }
    }
}
