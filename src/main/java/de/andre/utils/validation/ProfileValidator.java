package de.andre.utils.validation;

import de.andre.entity.profile.Profile;
import de.andre.repository.profile.ProfileRepository;
import de.andre.utils.ProfileHelper;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static de.andre.utils.HybrisConstants.MASK_MAIL;
import static de.andre.utils.HybrisConstants.MIN_AGE;

public class ProfileValidator implements Validator {
    private final ProfileRepository profileRepository;
    private final AddressValidator addressValidator;

    public ProfileValidator(final ProfileRepository profileRepository, final AddressValidator addressValidator) {
        this.profileRepository = profileRepository;
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return Profile.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        final Profile profile = (Profile) target;
        final boolean loggedIn = ProfileHelper.currentProfile() != null;

        if (!loggedIn && !StringUtils.hasText(profile.getPassword())) {
            errors.rejectValue("password", "user.password.empty");
        }

        if (ProfileHelper.calculateAge(profile.getDateOfBirth()) < MIN_AGE) {
            errors.rejectValue("dateOfBirth", "user.date.invalid");
        }

        if (!MASK_MAIL.matcher(profile.getEmail()).matches()) {
            errors.rejectValue("email", "user.email.invalidFormat");
        } else if (!loggedIn && profileRepository.emailExists(profile.getEmail())) {
            errors.rejectValue("email", "user.email.alreadyUsed");
        }

        if (!loggedIn) {
            validateShippingAddress(profile, errors);
        }
    }

    private void validateShippingAddress(final Profile profile, final Errors errors) {
        try {
            if (profile.getShippingAddress() == null) {
                errors.reject("user.address.unspecified");
            }
            errors.pushNestedPath("shippingAddress");
            ValidationUtils.invokeValidator(addressValidator, profile.getShippingAddress(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
