package de.andre.service.commerce.order.price.order;

import de.andre.entity.order.*;
import de.andre.service.commerce.order.price.PricingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderItemsTotalCalculator implements OrderCalculator {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemsTotalCalculator.class);

    @Override
    public List<PriceAdjustment> priceOrder(final Order order, final PricingContext ctx) {
        final List<CommerceItem> cis = order.getCommerceItems();
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();
        if (CollectionUtils.isEmpty(cis) && CollectionUtils.isEmpty(hgs)) {
            logger.debug("Order {} does not contain any commerce items or shipping groups, returning", order.getId());
            return Collections.emptyList();
        }

        BigDecimal itemsTotal = BigDecimal.ZERO;
        for (final CommerceItem ci : cis) {
            final ItemPriceInfo priceInfo = ci.getPriceInfo();
            itemsTotal = itemsTotal.add(priceInfo.getAmount());
        }
        logger.debug("Items total on {} order --> {}", order.getId(), itemsTotal);

        BigDecimal shippingTotal = BigDecimal.ZERO;
        for (final HardgoodShippingGroup hg : hgs) {
            final ShippingPriceInfo priceInfo = hg.getPriceInfo();
            shippingTotal = shippingTotal.add(priceInfo.getAmount());
        }
        logger.debug("Shipping total on {} order --> {}", order.getId(), shippingTotal);

        final OrderPriceInfo orderPriceInfo = order.getPriceInfo();
        final List<PriceAdjustment> adjustments = Arrays.asList(
                applyItemsTotal(orderPriceInfo, itemsTotal),
                applyShippingTotal(orderPriceInfo, shippingTotal)
        );

        logger.debug("Final total on order --> {}", orderPriceInfo.getAmount());
        return adjustments;
    }

    private PriceAdjustment applyItemsTotal(final OrderPriceInfo priceInfo, final BigDecimal itemsTotal) {
        priceInfo.setRawAmount(itemsTotal);
        priceInfo.setAmount(itemsTotal);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(1L);
        adj.setAdjustment(itemsTotal);
        adj.setAdjDescription("Items total");
        return adj;
    }

    private PriceAdjustment applyShippingTotal(final OrderPriceInfo priceInfo, final BigDecimal shippingTotal) {
        priceInfo.setShipping(shippingTotal);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(1L);
        adj.setAdjustment(shippingTotal);
        adj.setAdjDescription("Shipping total");
        return adj;
    }
}
