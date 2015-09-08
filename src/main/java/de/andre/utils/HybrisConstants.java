package de.andre.utils;

import java.util.regex.Pattern;

/**
 * Created by andreika on 8/5/2015.
 */
public class HybrisConstants {

	public static final Pattern MASK_PASSWORD = Pattern.compile(".{6,15}");

	public static final Pattern MASK_CITY = Pattern.compile("[A-Za-z.'\\-(\\s)]{1,40}$");

	public static final Pattern MASK_NAME = Pattern.compile("[A-Za-z\\s]*{1,40}$");

	public static final Pattern MASK_MAIL = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

	public static final int passwordLength = 6;
}
