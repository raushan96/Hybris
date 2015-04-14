package de.andre.utils.validation;

import de.andre.entity.core.DpsAddress;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Andrei on 4/14/2015.
 */

@Component
public class DpsAddressValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return DpsAddress.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DpsAddress dpsAddress = (DpsAddress) target;
	}
}
