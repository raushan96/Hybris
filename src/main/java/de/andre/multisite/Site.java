package de.andre.multisite;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface Site {
    String getId();
    String getDisplayName();
    Locale getLocale();
    boolean isEnabled();

    Set<String> getUrls();
    Map<String, String> getAttributes();
}
