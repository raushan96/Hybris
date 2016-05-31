package de.andre.utils.validation;

import de.andre.entity.profile.Address;
import de.andre.service.account.AddressTools;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static de.andre.utils.HybrisConstants.ISO_COUNTRY_BY;
import static de.andre.utils.HybrisConstants.MASK_CITY;

public class AddressValidator implements Validator {
    public static final Range POSTAL_RANGE = new Range(100000, 999999);

    private final AddressTools addressTools;

    public AddressValidator(final AddressTools addressTools) {
        this.addressTools = addressTools;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        final Address address = (Address) target;

        if (!MASK_CITY.matcher(address.getContactInfo().getCity()).matches()) {
            errors.rejectValue("city", "address.city.invalidFormat");
        }

        if (!ISO_COUNTRY_BY.equals(address.getContactInfo().getCountryCode())) {
            errors.rejectValue("countryCode", "address.countryCode.invalidFormat");
        }

        try {
            if (!POSTAL_RANGE.containsValue(Long.parseLong(address.getContactInfo().getPostalCode()))) {
                errors.rejectValue("postalCode", "address.postalCode.invalidRange");
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("postalCode", "address.postalCode.invalidFormat");
        }
    }
}
