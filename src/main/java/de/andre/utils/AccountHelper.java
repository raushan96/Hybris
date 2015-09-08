package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import static de.andre.utils.HybrisConstants.passwordLength;

public class AccountHelper {
	private static final Logger log = LoggerFactory.getLogger(AccountHelper.class);
	private static final Random sRandom = new SecureRandom();

	private AccountHelper() {}

	public static String generateRandomString() {
		String uuid = UUID.randomUUID().toString();
		return  uuid.replaceAll("-", "").substring(0, passwordLength + 1);
	}
}
