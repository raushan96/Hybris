package de.andre.service.commerce.order.price;

import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.entity.order.PriceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Currency;
import java.util.List;
import java.util.Objects;

public abstract class SkeletonPricingEngine implements PricingEngine {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final String className = this.getClass().getSimpleName();

    @Override
    public void priceOrder(final Order order, final PricingContext ctx) {
        Assert.notNull(order);
        Assert.notNull(ctx);

        if (logger.isDebugEnabled()) {
            logger.debug("Applying {} engine on {} order, with {} context", className, order.getId(), ctx);
        }

        priceOrderInternal(order, ctx);

        if (logger.isDebugEnabled()) {
            logger.debug("Finished applying {} engine to {} order", className, order.getId());
        }
    }

    protected abstract void priceOrderInternal(final Order order, final PricingContext ctx);

    protected void applyCurrency(final PriceInfo priceInfo, final PricingContext ctx) {
        if (!StringUtils.hasLength(priceInfo.getCurrencyCode())) {
            final Currency currency = Currency.getInstance(ctx.getLocale());
            priceInfo.setCurrencyCode(currency.getCurrencyCode());
        }
    }

    protected boolean adjustmentsChanged(final List<PriceAdjustment> oldAdj, final List<PriceAdjustment> newAdj) {
        return !Objects.equals(oldAdj, newAdj);
    }
}
