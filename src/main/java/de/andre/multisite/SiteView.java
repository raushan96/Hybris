package de.andre.multisite;

import de.andre.entity.profile.SiteConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SiteView implements Site {
    private String id;
    private String displayName;
    private Locale locale;
    private boolean enabled;

    private Set<String> urls;
    private Map<String, String> attributes;

    public SiteView(final SiteConfiguration siteConfiguration) {
        Assert.notNull(siteConfiguration);
        BeanUtils.copyProperties(siteConfiguration, this);

        this.attributes = (siteConfiguration.getAttributes() != null && !siteConfiguration.getAttributes().isEmpty()) ?
                Collections.unmodifiableMap(siteConfiguration.getAttributes()) :
                Collections.emptyMap();
        this.urls = (siteConfiguration.getUrls() != null && !siteConfiguration.getUrls().isEmpty()) ?
                Collections.unmodifiableSet(siteConfiguration.getUrls()) :
                Collections.emptySet();
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Set<String> getUrls() {
        return this.urls;
    }

    @Override
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
