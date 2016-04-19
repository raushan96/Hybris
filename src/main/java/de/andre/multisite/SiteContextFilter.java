package de.andre.multisite;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class SiteContextFilter extends OncePerRequestFilter {
    private final SiteManager siteManager;

    private Set<SiteResolver> siteResolvers = Collections.emptySet();
    private String defaultSiteId;

    public SiteContextFilter(final SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            String siteId = null;
            for (final Iterator<SiteResolver> resolversIterator = siteResolvers.iterator();
                 resolversIterator.hasNext() && siteId == null;) {
                siteId = resolversIterator.next().resolveSiteId(request);
            }

            if (!StringUtils.hasLength(siteId)) {
                logger.debug("SiteConfiguration wasn\'t resolved for the " + request.getRequestURI() + " url");
                siteId = defaultSiteId;
            }

            logger.debug("Pushing site with id: " + siteId);
            siteManager.setSite(siteManager.siteFromId(siteId));

            filterChain.doFilter(request, response);
        } finally {
            siteManager.clearSite();
        }
    }

    public String getDefaultSiteId() {
        return defaultSiteId;
    }

    public void setDefaultSiteId(String defaultSiteId) {
        this.defaultSiteId = defaultSiteId;
    }

    public Set<SiteResolver> getSiteResolvers() {
        return Collections.unmodifiableSet(this.siteResolvers);
    }

    public void setSiteResolvers(Set<SiteResolver> siteResolvers) {
        this.siteResolvers = siteResolvers;
    }
}
