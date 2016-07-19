package de.andre.service.commerce.order.price;

import de.andre.entity.order.Order;
import de.andre.multisite.SiteManager;
import de.andre.service.account.ProfileTools;
import de.andre.service.commerce.order.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

public class RepriceEngineImpl implements RepriceEngine {
    private static final Logger logger = LoggerFactory.getLogger(RepriceEngineImpl.class);

    private final ProfileTools profileTools;

    private List<PricingEngine> pricingEngines = Collections.emptyList();

    public RepriceEngineImpl(final ProfileTools profileTools) {
        this.profileTools = profileTools;
    }

    @Override
    public void repriceOrder(final Order order) {
        Assert.notNull(order, "Null order inside reprice");
        logger.debug("Starting {} order reprice", order.getId());
        priceOrder(order, createPricingContext());
        logger.debug("Finished {} order reprice", order.getId());
    }

    @Override
    public void weakReprice(final Order order) {
        Assert.notNull(order, "Null order inside reprice");

        if (!OrderUtils.orderPriced(order)) {
            logger.debug("Order {} is not priced, starting full reprice", order.getId());
            repriceOrder(order);
        }
    }

    private PricingContext createPricingContext() {
        return new PricingContext(
                LocaleContextHolder.getLocale(),
                profileTools.currentProfile(),
                SiteManager.getSite()
        );
    }

    @Override
    public void priceOrder(final Order order, final PricingContext pricingContext) {
        Assert.notNull(order, "Null order inside reprice");
        Assert.notNull(pricingContext, "Null price context inside reprice");

        for (final PricingEngine pricingEngine : pricingEngines) {
            pricingEngine.priceOrder(order, pricingContext);
        }
    }

    public void setPricingEngines(List<PricingEngine> pricingEngines) {
        this.pricingEngines = pricingEngines;
    }
}
