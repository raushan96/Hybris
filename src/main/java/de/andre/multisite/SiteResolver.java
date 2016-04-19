package de.andre.multisite;

import org.springframework.util.comparator.NullSafeComparator;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;

public interface SiteResolver {
    String resolveSiteId(HttpServletRequest request);

    int getPriority();

    Comparator<SiteResolver> PRIORITY_ORDER = new NullSafeComparator<>(new Comparator<SiteResolver>() {
        @Override
        public int compare(SiteResolver o1, SiteResolver o2) {
            return o2.getPriority() > o1.getPriority() ?
                    -1 : o2.getPriority() == o1.getPriority() ?
                    0 :
                    1;
        }
    }, true);

}
