package de.andre.utils.validation;

import de.andre.entity.core.DpsAddress;
import de.andre.service.account.AddressTools;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Map;

import static de.andre.utils.HybrisConstants.MASK_CITY;
import static de.andre.utils.HybrisConstants.ISO_COUNTRY;

public class DpsAddressValidator implements Validator {
	public static final Range POSTAL_RANGE = new Range(100000, 999999);

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
	public void validate(final Object target, final Errors errors) {
		for (final Map.Entry<String, String> requiredField : requiredFields.entrySet()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, requiredField.getKey(), requiredField.getValue());
		}

		DpsAddress dpsAddress = (DpsAddress) target;

		if (errors.getFieldError("city") == null && !MASK_CITY.matcher(dpsAddress.getCity()).matches()) {
			errors.rejectValue("city", "address.city.invalidFormat");
		}

		if (errors.getFieldError("countryCode") == null && !ISO_COUNTRY.equals(dpsAddress.getCountryCode())) {
			errors.rejectValue("countryCode", "address.countryCode.invalidFormat");
		}

		if (errors.getFieldError("postalCode") == null && !POSTAL_RANGE.containsValue(Long.valueOf(dpsAddress.getPostalCode()))) {
			errors.rejectValue("postalCode", "address.postalCode.invalidRange");
		}
	}
}
