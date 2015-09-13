package de.andre.utils.validation;

import de.andre.entity.core.DpsUser;
import de.andre.service.account.AccountTools;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Map;

import static de.andre.utils.HybrisConstants.MASK_MAIL;

public class DpsUserValidator implements Validator {

	private AccountTools accountTools;

	private Map<String, String> requiredFields;

	public DpsUserValidator(final AccountTools accountTools, final Map<String, String> requiredFields) {
		this.accountTools = accountTools;
		this.requiredFields = requiredFields;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return DpsUser.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		for (final Map.Entry<String, String> requiredField : requiredFields.entrySet()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, requiredField.getKey(), requiredField.getValue());
		}

		DpsUser dpsUser = (DpsUser) target;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
		if (errors.getFieldError("dateOfBirth") == null &&
				dpsUser.getDateOfBirth().after(calendar.getTime())) {
			errors.rejectValue("dateOfBirth", "date.invalid");
		}

		if (MASK_MAIL.matcher(dpsUser.getEmail()).matches()) {
			final String oldEmail = accountTools.getCommerceUser().getEmail();
			if (errors.getFieldError("email") == null &&
					!oldEmail.equals(dpsUser.getEmail()) &&
					accountTools.findUserByEmail(dpsUser.getEmail()) != null) {
				errors.rejectValue("email", "email.alreadyUsed");
			}
		} else {
			errors.rejectValue("email", "email.invalidFormat");
		}
	}
}
