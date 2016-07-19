package de.andre.service.commerce.order.processor;

import de.andre.multisite.Site;
import org.springframework.util.Assert;

import java.util.Locale;

public class BaseContext {
    private final Locale locale;
    private final Site site;

    public BaseContext(final Locale locale, final Site site) {
        Assert.notNull(locale);
        Assert.notNull(site);

        this.locale = locale;
        this.site = site;
    }

    public Locale getLocale() {
        return locale;
    }

    public Site getSite() {
        return site;
    }
}
