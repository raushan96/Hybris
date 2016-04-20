package de.andre.multisite;

import de.andre.entity.profile.SiteConfiguration;
import de.andre.repository.profile.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

public class SiteManager {
    protected static final Logger logger = LoggerFactory.getLogger(SiteManager.class);

    private static final ThreadLocal<Site> siteHolder = new ThreadLocal<>();
    private static final Site EMPTY_SITE = new Site.EmptySite();

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
        Site site = siteHolder.get();

        if (site == null) {
            site = EMPTY_SITE;
            siteHolder.set(EMPTY_SITE);
        }

        return site;
    }

    public static String getSiteId() {
        final Site site = siteHolder.get();
        if (site != null) {
            return site.getId();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<SiteConfiguration> allSites() {
        return siteRepository.fetchSites();
    }

    public String siteIdFromUrl(final String url) {
        if (!StringUtils.hasLength(url)) {
            return null;
        }

        return siteRepository.siteIdFromUrl(url);
    }

    public Site siteFromId(final String siteId) {
        if (!StringUtils.hasLength(siteId)) {
            return EMPTY_SITE;
        }

        final SiteConfiguration site = siteRepository.fetchSite(siteId);
        if (site != null) {
            logger.debug("Found site with name: {} for {} id", site.getDisplayName(), siteId);
            return site;
        }

        return EMPTY_SITE;
    }
}
