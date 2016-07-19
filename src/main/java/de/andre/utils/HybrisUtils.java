package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static de.andre.utils.HybrisConstants.*;

public class HybrisUtils {
    private static final Logger logger = LoggerFactory.getLogger(HybrisUtils.class);

    private HybrisUtils() {
    }

    public static void logParams(final Logger logger, final Object... params) {
        if (logger != null && logger.isDebugEnabled()) {
            if (ObjectUtils.isEmpty(params)) {
                logger.debug("Empty params received");
            }
            logger.debug("Params received {}", ObjectUtils.nullSafeToString(params));
        }
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
        }
        else {
            sb.append(DEFAULT_PREFIX);
        }
        sb.append(NICKNAME_SEPARATOR);
        final String ts = Long.toString(System.currentTimeMillis());
        sb.append(ts.substring(ts.length() - 4));

        return sb.toString();
    }
}
