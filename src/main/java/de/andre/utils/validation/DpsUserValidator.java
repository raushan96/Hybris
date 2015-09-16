package de.andre.utils.validation;

import de.andre.entity.core.DpsUser;
import de.andre.service.account.AccountTools;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Calendar;
import java.util.Map;

import static de.andre.utils.HybrisConstants.MIN_AGE;
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
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - MIN_AGE);
		if (errors.getFieldError("dateOfBirth") == null &&
				dpsUser.getDateOfBirth().after(calendar.getTime())) {
			errors.rejectValue("dateOfBirth", "user.date.invalid");
		}

		if (errors.getFieldError("email") == null) {
			try {
				new InternetAddress(dpsUser.getEmail()).validate();
			} catch (final AddressException e) {
				errors.rejectValue("email", "user.email.invalidFormat");
			}

			final DpsUser currentUser = accountTools.getCommerceUser();
			final String oldEmail = currentUser != null ? currentUser.getEmail() : null;

			if (errors.getFieldError("email") == null && !MASK_MAIL.matcher(dpsUser.getEmail()).matches()) {
				errors.rejectValue("email", "user.email.invalidFormat");
			} else if (!dpsUser.getEmail().equals(oldEmail) && accountTools.findUserByEmail(dpsUser.getEmail()) != null) {
				errors.rejectValue("email", "user.email.alreadyUsed");
			}
		}
	}
}
