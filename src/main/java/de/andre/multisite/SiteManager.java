package de.andre.multisite;

import de.andre.entity.profile.SiteConfiguration;
import de.andre.repository.profile.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

public class SiteManager {
    protected static final Logger logger = LoggerFactory.getLogger(SiteManager.class);

    private static final ThreadLocal<Site> siteHolder = new ThreadLocal<>();

    private final SiteRepository siteRepository;

    public SiteManager(final SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public void setSite(final Site site) {
        Assert.notNull(site);
        siteHolder.set(site);
    }

    public void setSite(final SiteConfiguration siteConfiguration) {
        Assert.notNull(siteConfiguration);
        siteHolder.set(new SiteView(siteConfiguration));
    }

    public void clearSite() {
        siteHolder.remove();
    }

    public static Site getSite() {
        return siteHolder.get();
    }

    public static String getSiteId() {
        final Site site = siteHolder.get();
        if (site != null) {
            return site.getId();
        }
        return null;
    }

    public List<SiteConfiguration> allSites() {
        return siteRepository.findAll();
    }

    public String siteIdFromUrl(final String url) {
        if (!StringUtils.hasLength(url)) {
            return null;
        }

        return siteRepository.siteIdFromUrl(url);
    }

    public SiteConfiguration siteFromId(final String siteId) {
        if (!StringUtils.hasLength(siteId)) {
            return null;
        }

        return siteRepository.fetchSite(siteId)
                .orElse(null);
    }
}
