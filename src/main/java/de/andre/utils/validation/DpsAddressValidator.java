package de.andre.utils.validation;

import de.andre.entity.core.DpsAddress;
import de.andre.repository.OrderRepository;
import de.andre.repository.ProductRepository;
import de.andre.repository.UserRepository;
import de.andre.service.account.AddressTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Map;

public class DpsAddressValidator implements Validator {
	private final AddressTools addressTools;

	private Map<String, String> requiredFields;

	public DpsAddressValidator(final AddressTools addressTools, final Map<String, String> requiredFields) {
		this.addressTools = addressTools;
		this.requiredFields = requiredFields;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return DpsAddress.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		for (final Map.Entry<String, String> requiredField : requiredFields.entrySet()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, requiredField.getKey(), requiredField.getValue());
		}

		DpsAddress dpsAddress = (DpsAddress) target;
	}
}
