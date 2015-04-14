package de.andre.utils.validation;

import de.andre.entity.core.DpsUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by andreika on 4/11/2015.
 */

@Component
public class DpsUserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return DpsUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "name.empty");
		DpsUser dpsUser = (DpsUser) target;
		if (StringUtils.isEmpty(dpsUser.getLastName())) {
			errors.rejectValue("lastName", "lastName.empty");
		}
	}
}
