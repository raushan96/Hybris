package de.andre.entity.site;

import de.andre.entity.catalog.Catalog;
import de.andre.multisite.Site;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "hs_site", schema = "hybris")
public class SiteConfiguration implements Site {
    private String id;
    private String displayName;
    private Locale locale;
    private boolean enabled;
    private LocalDateTime created;

    private Set<String> urls;
    private Map<String, String> attributes;

    private Catalog defaultCatalog;

    //define version instead?
    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ElementCollection
    @CollectionTable(
            name = "hs_site_urls",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "url")
    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    @ElementCollection
    @CollectionTable(
            name = "hs_site_attributes",
            joinColumns = @JoinColumn(name = "site_id")
    )
    @MapKeyColumn(name = "attr_key")
    @Column(name = "attr_value")
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    public Catalog getDefaultCatalog() {
        return defaultCatalog;
    }

    public void setDefaultCatalog(Catalog defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Type(type = "locale")
    @Column(name = "locale")
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Type(type = "boolean")
    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Generated(GenerationTime.INSERT)
    @Column(name = "created", insertable = false, updatable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteConfiguration siteConfiguration = (SiteConfiguration) o;

        if (enabled != siteConfiguration.enabled) return false;
        if (displayName != null ? !displayName.equals(siteConfiguration.displayName) : siteConfiguration.displayName != null) return false;
        return locale != null ? locale.equals(siteConfiguration.locale) : siteConfiguration.locale == null;

    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
