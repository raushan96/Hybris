package de.andre.utils;

import de.andre.entity.profile.Profile;
import de.andre.service.security.HybrisUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;

import static de.andre.utils.HybrisConstants.*;
import static de.andre.utils.HybrisConstants.KEY_MAX_LENGTH;
import static de.andre.utils.HybrisConstants.KEY_MIN_LENGTH;
import static de.andre.utils.HybrisConstants.PASSWORD_LENGTH;

public class ProfileHelper {
    private static final Logger logger = LoggerFactory.getLogger(ProfileHelper.class);

    private ProfileHelper() {
    }

    public static Profile currentProfile() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof HybrisUser) {
                return ((HybrisUser) principal).getCommerceProfile();
            }

            logger.warn("Unknown principal instance.");
            return null;
        }

        logger.warn("Profile is not authorized, returning null.");
        return null;
    }

    public static Profile authenticatedProfile() {
        Assert.notNull(SecurityContextHolder.getContext().getAuthentication());
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.isTrue(principal instanceof HybrisUser);

        return ((HybrisUser) principal).getCommerceProfile();
    }

    public static boolean isSoftLogged() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }

    public static int calculateAge(final LocalDate dob) {
        Assert.notNull(dob);
        return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
    }

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, PASSWORD_LENGTH);
    }

    public static String generateUniqueNickname(String pNickname, final String pDefaultPrefix,
            final Collection<String> existingNicknames) {
        if (nicknameValid(pNickname, existingNicknames)) {
            logger.debug("Given nickname {} is suitable for key", pNickname);
            return pNickname;
        }

        boolean uniqueNicknameFound = false;
        final StringBuilder sb = new StringBuilder(KEY_MAX_LENGTH);
        while (!uniqueNicknameFound) {
            pNickname = generateNewNickname(pDefaultPrefix, sb);
            uniqueNicknameFound = nicknameValid(pNickname, existingNicknames);
        }

        logger.debug("Generated nickname {} for key", pNickname);
        return pNickname;
    }

    private static boolean nicknameValid(final String pNickname, final Collection<String> existingNicknames) {
        if (!StringUtils.hasText(pNickname) || StringUtils.containsWhitespace(pNickname) ||
                pNickname.length() < KEY_MIN_LENGTH || pNickname.length() > KEY_MAX_LENGTH) {
            return false;
        }

        if (existingNicknames == null || existingNicknames.isEmpty()) {
            return true;
        }

        return !existingNicknames.contains(pNickname);
    }

    private static String generateNewNickname(final String pDefaultPrefix, final StringBuilder sb) {
        sb.setLength(0);
        if (!StringUtils.isEmpty(pDefaultPrefix)) {
            sb.append(pDefaultPrefix);
        } else {
            sb.append(DEFAULT_PREFIX);
        }
        sb.append(NICKNAME_SEPARATOR);
        final String ts = Long.toString(System.currentTimeMillis());
        sb.append(ts.substring(ts.length() - 4));

        return sb.toString();
    }
}
