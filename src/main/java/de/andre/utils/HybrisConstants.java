package de.andre.utils;

import java.util.regex.Pattern;

public class HybrisConstants {
    private HybrisConstants() {
    }

    public static final Pattern MASK_PASSWORD = Pattern.compile(".{6,15}");
    public static final Pattern MASK_CITY = Pattern.compile("[A-Za-z.'\\-(\\s)]{1,40}$");
    public static final Pattern MASK_NAME = Pattern.compile("[A-Za-z\\s]*{1,40}$");
    public static final Pattern MASK_MAIL = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

    public static final int PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 6;

    public static final String FORGOT_PASSWORD_EMAIL = "forgotPassword";
    public static final String WELCOME_EMAIL = "welcome";

    public static final String ISO_COUNTRY_BY = "BY";

    public static final int KEY_MAX_LENGTH = 14;
    public static final int KEY_MIN_LENGTH = 5;
    public static final String DEFAULT_PREFIX = "key";
    public static final String DEFAULT_SHIPPING_NAME = "shippingAddress";
    public static final String NICKNAME_SEPARATOR = "-";
    public static final String ADDRESS_NAME_PREFIX = "adr";
}
