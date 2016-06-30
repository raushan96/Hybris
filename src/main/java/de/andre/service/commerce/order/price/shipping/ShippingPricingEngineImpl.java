package de.andre.service.commerce.order.price.shipping;

import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.entity.order.ShippingPriceInfo;
import de.andre.service.commerce.order.price.Calculator;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletonPricingEngine;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class ShippingPricingEngineImpl extends SkeletonPricingEngine {
    private List<Calculator> shippingCalculators = Collections.emptyList();

    @Override
    protected void priceOrderInternal(final Order order, final PricingContext ctx) {
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();
        if (CollectionUtils.isEmpty(hgs)) {
            logger.debug("Order {} does not contain any hardgood shipping groups, returning", order.getId());
            return;
        }

        for (final HardgoodShippingGroup hg : hgs) {
            final ShippingPriceInfo priceInfo = hg.getPriceInfo();
            Assert.notNull(priceInfo);
            applyCurrency(priceInfo, ctx);

            final List<PriceAdjustment> previousAdjustments = priceInfo.getPriceAdjustments();

            final List<PriceAdjustment> calculatedAdjustments = Collections.emptyList();
            if (logger.isDebugEnabled()) {
                logger.debug("Final adjustments calculated list: {}", StringUtils.collectionToCommaDelimitedString(calculatedAdjustments));
            }

            if (adjustmentsChanged(previousAdjustments, calculatedAdjustments)) {
                priceInfo.setPriceAdjustments(calculatedAdjustments);
            }
        }

        for (final Calculator shippingCalculator : shippingCalculators) {
            shippingCalculator.priceOrderItems(order, ctx);
        }
    }

    public void setShippingCalculators(List<Calculator> shippingCalculators) {
        this.shippingCalculators = shippingCalculators;
    }
}
