package de.andre.utils;

import de.andre.service.security.HybrisUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static de.andre.utils.HybrisConstants.PASSWORD_LENGTH;

public class ProfileUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProfileUtils.class);

    private ProfileUtils() {
    }

    public static Long currentId() {
        final HybrisUser.UserIdentity userIdentity = getCurrentUserIdentity();
        return userIdentity != null ? userIdentity.getId() : null;
    }

    public static String currentEmail() {
        final HybrisUser.UserIdentity userIdentity = getCurrentUserIdentity();
        return userIdentity != null ? userIdentity.getEmail() : null;
    }

    public static boolean loggedIn() {
        return getCurrentUserIdentity() != null;
    }

    private static HybrisUser.UserIdentity getCurrentUserIdentity() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof HybrisUser) {
                return ((HybrisUser) principal).getUserIdentity();
            }
        }

        logger.warn("Profile is not authorized/unknown authority, returning null.");
        return null;
    }

//    public static Profile authenticatedProfile() {
//        Assert.notNull(SecurityContextHolder.getContext().getAuthentication());
//        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Assert.isTrue(principal instanceof HybrisUser);
//
//        return ((HybrisUser) principal).getCommerceProfile();
//    }

    public static boolean isSoftLogged() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }

    public static boolean isLogged() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
    }

    public static int calculateAge(final LocalDate dob) {
        Assert.notNull(dob);
        return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
    }

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, PASSWORD_LENGTH);
    }
}
