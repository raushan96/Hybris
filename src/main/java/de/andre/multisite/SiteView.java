package de.andre.multisite;

import de.andre.entity.site.SiteConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.*;

public class SiteView implements Site {
    private String id;
    private String catId;
    private String priceListId;
    private String displayName;
    private Locale locale;
    private boolean enabled;

    private Set<String> urls;
    private Map<String, String> attributes;

    public SiteView(final SiteConfiguration siteConfiguration) {
        Assert.notNull(siteConfiguration);
        BeanUtils.copyProperties(siteConfiguration, this);
        catId = siteConfiguration.catalogId();
        priceListId = siteConfiguration.priceListId();

        this.attributes = (siteConfiguration.getAttributes() != null && !siteConfiguration.getAttributes().isEmpty()) ?
                Collections.unmodifiableMap(siteConfiguration.getAttributes()) :
                Collections.emptyMap();
        this.urls = (siteConfiguration.getUrls() != null && !siteConfiguration.getUrls().isEmpty()) ?
                Collections.unmodifiableSet(siteConfiguration.getUrls()) :
                Collections.emptySet();
    }

    public SiteView(final SiteBuilder builder) {
        Assert.notNull(builder);
        BeanUtils.copyProperties(builder, this);
        this.urls = builder.urls != null ?
                Collections.unmodifiableSet(builder.urls) :
                Collections.emptySet();
        this.attributes = builder.attributes != null ?
                Collections.unmodifiableMap(builder.attributes) :
                Collections.emptyMap();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String catalogId() {
        return catId;
    }

    @Override
    public String priceListId() {
        return priceListId;
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

    @Override
    public String toString() {
        return "SiteView{" +
                "id='" + id + '\'' +
                ", catId='" + catId + '\'' +
                ", priceListId='" + priceListId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", locale=" + locale +
                '}';
    }

    public static class SiteBuilder {
        private String id;
        private String catId;
        private String priceListId;
        private String displayName;
        private Locale locale;
        private boolean enabled;

        private Set<String> urls;
        private Map<String, String> attributes;

        public SiteBuilder(final String id, final String displayName) {
            this.id = id;
            this.displayName = displayName;
            this.enabled = true;
        }

        public SiteBuilder catId(String catId) {
            this.catId = catId;
            return this;
        }

        public SiteBuilder priceListId(String priceListId) {
            this.priceListId = priceListId;
            return this;
        }

        public SiteBuilder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public SiteBuilder disable() {
            this.enabled = false;
            return this;
        }

        public SiteBuilder addUrl(String url) {
            if (this.urls == null)
                this.urls = new HashSet<>();
            this.urls.add(url);
            return this;
        }

        public SiteBuilder addUrls(Collection<String> urls) {
            if (urls != null && !urls.isEmpty()) {
                urls.forEach(this::addUrl);
            }
            return this;
        }

        public SiteBuilder attributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }

        public SiteView build() {
            return new SiteView(this);
        }
    }
}
