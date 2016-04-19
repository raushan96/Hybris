package de.andre.multisite;

import de.andre.entity.profile.SiteConfiguration;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

public class UrlFilterStrategy implements SiteResolver {
    private final SiteManager siteManager;

    public UrlFilterStrategy(final SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    @Override
    public String resolveSiteId(final HttpServletRequest request) {
        Assert.notNull(request);
        return siteManager.siteIdFromUrl(request.getServerName());
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
