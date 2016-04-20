package de.andre.multisite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UrlFilterStrategy implements SiteResolver {
    protected static final Logger logger = LoggerFactory.getLogger(UrlFilterStrategy.class);

    private final SiteManager siteManager;

    protected ReadWriteLock urlCacheLock = new ReentrantReadWriteLock();
    protected Map<String, String> urlToSiteId = new HashMap<>();

    public UrlFilterStrategy(final SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    @Override
    public String resolveSiteId(final HttpServletRequest request) {
        Assert.notNull(request);
        final String url = request.getServerName();
        Assert.notNull(url);

        final String cachedSiteId = cachedId(url);
        if (StringUtils.hasLength(cachedSiteId)) {
            logger.debug("Found '{}' cached site id", cachedSiteId);
            return cachedSiteId;
        }

        return siteManager.siteIdFromUrl(url);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public void refresh() {
        urlCacheLock.writeLock().lock();
        try {
            logger.debug("Refreshing site url cache, current size is {}", urlToSiteId.size());

            urlToSiteId.clear();
            siteManager.allSites()
                    .stream()
                    .filter(site -> !CollectionUtils.isEmpty(site.getUrls()))
                    .forEach(site -> {
                        for (final String url : site.getUrls()) {
                            urlToSiteId.put(url, site.getId());
                        }
                    });
        } finally {
            urlCacheLock.writeLock().unlock();
        }

    }

    private String cachedId(final String url) {
        logger.debug("Trying to find cached site id for '{}' url", url);
        urlCacheLock.readLock().lock();

        try {
            return urlToSiteId.get(url);
        } finally {
            urlCacheLock.readLock().unlock();
        }
    }
}
