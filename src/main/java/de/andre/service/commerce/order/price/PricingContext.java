package de.andre.service.commerce.order.price;

import de.andre.entity.profile.Profile;
import de.andre.multisite.Site;
import org.springframework.util.Assert;

import java.util.Currency;
import java.util.Locale;

public class PricingContext {
    private Locale locale;
    private Profile profile;
    private Site site;

    public PricingContext(Locale locale, Profile profile, Site site) {
        Assert.notNull(locale);
        Assert.notNull(profile);
        Assert.notNull(site);

        this.locale = locale;
        this.profile = profile;
        this.site = site;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "PricingContext{" +
                "locale=" + locale +
                ", profile=" + profile.getId() +
                ", site=" + site.getId() +
                '}';
    }
}
