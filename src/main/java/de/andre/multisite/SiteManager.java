package de.andre.multisite;

import de.andre.entity.site.SiteConfiguration;
import de.andre.repository.profile.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static de.andre.multisite.SiteConstants.SITE_CACHE_NAME;

public class SiteManager {
    protected static final Logger logger = LoggerFactory.getLogger(SiteManager.class);

    private static final ThreadLocal<Site> siteHolder = new NamedThreadLocal<>("SITE_COMMERCE");
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
        if (site == null) {
            return EMPTY_SITE.getId();
        }

        return site.getId();
    }

    @Cacheable(SITE_CACHE_NAME)
    @Transactional(readOnly = true)
    public List<SiteConfiguration> allSites() {
        return siteRepository.fetchSites();
    }

    @Cacheable(SITE_CACHE_NAME)
    public String siteIdFromUrl(final String url) {
        if (!StringUtils.hasLength(url)) {
            return null;
        }

        return siteRepository.siteIdFromUrl(url);
    }

    @Cacheable(SITE_CACHE_NAME)
    public Site siteFromId(final String siteId) {
        if (!StringUtils.hasLength(siteId)) {
            return EMPTY_SITE;
        }

        final SiteConfiguration site = siteRepository.fetchSite(siteId);
        if (site != null) {
            logger.debug("Found site with name: {} for {} id", site.getDisplayName(), siteId);
            return new SiteView(site);
        }

        return EMPTY_SITE;
    }

    public Site instantiateSite(final String id, final String displayName) {
        return new SiteView.SiteBuilder(id, displayName)
                .locale(LocaleContextHolder.getLocale())
                .addUrl(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).
                        getRequest().getRequestURI())
                .catId(getSite().catalogId())
                .priceListId(getSite().priceListId())
                .build();
    }

    @CacheEvict(cacheNames = SITE_CACHE_NAME, allEntries = true)
    public void clearSiteCache() {
        logger.info("Cleaning site cache");
    }
}
