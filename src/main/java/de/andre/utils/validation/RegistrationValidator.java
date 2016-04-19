package de.andre.utils.validation;

import de.andre.entity.dto.RegistrationForm;
import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class RegistrationValidator implements Validator {
    private final Validator profileValidator;
    private final Validator addressValidator;

    private final SpringValidatorAdapter hibernateValidator;

    public RegistrationValidator(final SpringValidatorAdapter hibernateValidator, final Validator addressValidator, final Validator profileValidator) {
        this.hibernateValidator = hibernateValidator;
        this.addressValidator = addressValidator;
        this.profileValidator = profileValidator;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        hibernateValidator.validate(target, errors);

        if (errors.hasErrors()) {
            return;
        }

        final RegistrationForm registrationForm = (RegistrationForm) target;

        validateNestedObject(profileValidator, "profile", registrationForm.getProfile(), errors);
        validateNestedObject(addressValidator, "shippingAddress", registrationForm.getShippingAddress(), errors);
    }

    private void validateNestedObject(final Validator validator, final String targetName, final Object target,
                                      final Errors errors) {
        if (!validator.supports(target.getClass()))
            throw new IllegalArgumentException("Unsupported target for " + validator.getClass());

        try {
            errors.pushNestedPath(targetName);
            ValidationUtils.invokeValidator(validator, target, errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
