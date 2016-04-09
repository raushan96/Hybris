package de.andre.utils.validation;

import de.andre.entity.profile.Profile;
import de.andre.service.account.AccountTools;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Calendar;
import java.util.Map;

import static de.andre.utils.HybrisConstants.MASK_MAIL;
import static de.andre.utils.HybrisConstants.MIN_AGE;

public class ProfileValidator implements Validator {
    private AccountTools accountTools;

    private Map<String, String> requiredFields;

    public ProfileValidator(final AccountTools accountTools, final Map<String, String> requiredFields) {
        this.accountTools = accountTools;
        this.requiredFields = requiredFields;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return Profile.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        for (final Map.Entry<String, String> requiredField : requiredFields.entrySet()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, requiredField.getKey(), requiredField.getValue());
        }

        Profile Profile = (Profile) target;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - MIN_AGE);
        //TODO
//        if (errors.getFieldError("dateOfBirth") == null &&
//                Profile.getDateOfBirth().after(calendar.getTime())) {
//            errors.rejectValue("dateOfBirth", "Profile.date.invalid");
//        }

        if (errors.getFieldError("email") == null) {
            try {
                new InternetAddress(Profile.getEmail()).validate();
            } catch (final AddressException e) {
                errors.rejectValue("email", "Profile.email.invalidFormat");
            }

            final Profile currentProfile = accountTools.getCommerceProfile();
            final String oldEmail = currentProfile != null ? currentProfile.getEmail() : null;

            if (errors.getFieldError("email") == null && !MASK_MAIL.matcher(Profile.getEmail()).matches()) {
                errors.rejectValue("email", "Profile.email.invalidFormat");
            } else if (!Profile.getEmail().equals(oldEmail) && accountTools.findProfileByEmail(Profile.getEmail()) != null) {
                errors.rejectValue("email", "Profile.email.alreadyUsed");
            }
        }
    }
}
