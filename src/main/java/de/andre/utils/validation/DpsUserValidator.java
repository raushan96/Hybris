package de.andre.utils.validation;

import de.andre.entity.core.DpsUser;
import de.andre.service.account.AccountTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;

/**
 * Created by andreika on 4/11/2015.
 */

@Component
public class DpsUserValidator implements Validator {

	private AccountTools accountTools;

	@Autowired
	public DpsUserValidator(final AccountTools accountTools) {
		this.accountTools = accountTools;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return DpsUser.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "dateOfBirth.empty");

		DpsUser dpsUser = (DpsUser) target;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
		if (dpsUser.getDateOfBirth().after(calendar.getTime())) {
			errors.rejectValue("dateOfBirth", "date.invalid");
		}

		if (accountTools.findUserByEmail(dpsUser.getEmail()) != null) {
			errors.rejectValue("email", "email.alreadyUsed");
		}
	}
}
