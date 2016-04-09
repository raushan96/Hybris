package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static de.andre.utils.HybrisConstants.PASSWORD_LENGTH;

public class AccountHelper {
    private static final Logger log = LoggerFactory.getLogger(AccountHelper.class);

    private AccountHelper() {}

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return  uuid.replaceAll("-", "").substring(0, PASSWORD_LENGTH);
    }
}
