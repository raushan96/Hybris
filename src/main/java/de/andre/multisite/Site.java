package de.andre.multisite;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static de.andre.multisite.SiteConstants.EMPTY_SITE_ID;
import static de.andre.utils.HybrisConstants.EMPTY_STRING;

public interface Site {
    String getId();
    String getDisplayName();
    Locale getLocale();
    boolean isEnabled();

    Set<String> getUrls();
    Map<String, String> getAttributes();

    class EmptySite implements Site {

        @Override
        public String getId() {
            return EMPTY_SITE_ID;
        }

        @Override
        public String getDisplayName() {
            return EMPTY_STRING;
        }

        @Override
        public Locale getLocale() {
            return Locale.US;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public Set<String> getUrls() {
            return Collections.emptySet();
        }

        @Override
        public Map<String, String> getAttributes() {
            return Collections.emptyMap();
        }
    }
}
