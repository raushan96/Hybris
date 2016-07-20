package de.andre.service.commerce.order.pipeline;

import de.andre.entity.order.Order;
import de.andre.multisite.Site;
import de.andre.multisite.SiteManager;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import java.util.Locale;

public class ProcessContext<T> {
    private final Locale locale;
    private final Site site;

    private final T target;
    private final String targetName;

    private ProcessContext(final Locale locale, final Site site,
            final T target, final String targetName) {
        Assert.notNull(locale);
        Assert.notNull(site);
        Assert.notNull(target);
        Assert.hasLength(targetName);

        this.targetName = targetName;
        this.locale = locale;
        this.site = site;
        this.target = target;
    }

    public Locale getLocale() {
        return locale;
    }

    public Site getSite() {
        return site;
    }

    public T getTarget() {
        return target;
    }

    public String getTargetName() {
        return targetName;
    }

    public static ProcessBuilder builder() {
        return new ProcessBuilder();
    }

    public static ProcessBuilder defaultContext() {
        return new ProcessBuilder()
                .locale(LocaleContextHolder.getLocale())
                .site(SiteManager.getSite());
    }

    public static ProcessContext<Order> order(final Order order) {
        return defaultContext().targetName("order").build(order);
    }

    public static <T> ProcessContext<T> from(final T body, final String targetName) {
        return defaultContext().targetName(targetName).build(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessContext<?> that = (ProcessContext<?>) o;

        if (!locale.equals(that.locale)) return false;
        if (!site.equals(that.site)) return false;
        if (!target.equals(that.target)) return false;
        return targetName.equals(that.targetName);

    }

    @Override
    public int hashCode() {
        int result = locale.hashCode();
        result = 31 * result + site.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + targetName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProcessContext{" +
                "locale=" + locale +
                ", site=" + site +
                ", target=" + target +
                ", targetName='" + targetName + '\'' +
                '}';
    }

    private static class ProcessBuilder {
        private Locale locale;
        private Site site;
        private String targetName;

        public ProcessBuilder locale(final Locale locale) {
            this.locale = locale;
            return this;
        }

        public ProcessBuilder site(final Site site) {
            this.site = site;
            return this;
        }

        public ProcessBuilder targetName(final String targetName) {
            this.targetName = targetName;
            return this;
        }

        public <T> ProcessContext<T> build(final T target) {
            return new ProcessContext<>(this.locale, this.site, target, this.targetName);
        }
    }
}
