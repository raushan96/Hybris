package de.andre.service.commerce.order.price.order;

import de.andre.entity.order.*;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletonPricingEngine;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OrderItemsTotalCalculator extends SkeletonPricingEngine {
    @Override
    protected void priceOrderInternal(Order order, PricingContext ctx) {
        final List<CommerceItem> cis = order.getCommerceItems();
        if (CollectionUtils.isEmpty(cis)) {
            logger.debug("Order {} does not contain any commerce items, returning", order.getId());
            return;
        }

        BigDecimal itemsTotal = BigDecimal.ZERO;
        for (final CommerceItem ci : cis) {
            final ItemPriceInfo priceInfo = ci.getPriceInfo();
            Assert.notNull(priceInfo);
            itemsTotal = itemsTotal.add(priceInfo.getAmount());
        }

        logger.debug("Items total on {} order --> {}", order.getId(), itemsTotal);

        final OrderPriceInfo orderPriceInfo = order.getPriceInfo();
        Assert.notNull(orderPriceInfo);

        applyCurrency(orderPriceInfo, ctx);
        applyItemsTotal(orderPriceInfo, itemsTotal);

        logger.debug("Final total on order --> {}", orderPriceInfo.getAmount());
    }

    private void applyItemsTotal(final OrderPriceInfo orderPriceInfo, final BigDecimal itemsTotal) {
        if (!Objects.equals(orderPriceInfo.getRawAmount(), itemsTotal)) {
            orderPriceInfo.setRawAmount(itemsTotal);

            final PriceAdjustment adj = new PriceAdjustment();
            adj.setQtyAdjusted(1L);
            adj.setAdjustment(itemsTotal);
            adj.setAdjDescription("Items total");
            orderPriceInfo.priceAdjustmentsInternal().add(adj);
        }
    }
}
