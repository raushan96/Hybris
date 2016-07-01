package de.andre.service.commerce.order.price.order;

import de.andre.entity.order.Order;
import de.andre.entity.order.OrderPriceInfo;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.PriceUtils;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletonPricingEngine;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPricingEngineImpl extends SkeletonPricingEngine {
    private List<OrderCalculator> orderCalculators = Collections.emptyList();

    @Override
    public void priceOrderInternal(final Order order, final PricingContext ctx) {
        final OrderPriceInfo priceInfo = order.getPriceInfo();
        Assert.notNull(priceInfo);
        applyCurrency(priceInfo, ctx);
        priceInfo.fillAmounts(BigDecimal.ZERO);

        final List<PriceAdjustment> previousAdjustments = priceInfo.getPriceAdjustments();

        final List<PriceAdjustment> calculatedAdjustments = applyCalculators(order, orderCalculators, ctx);
        if (logger.isDebugEnabled()) {
            logger.debug("Final adjustments calculated list: {}",
                         StringUtils.collectionToCommaDelimitedString(calculatedAdjustments));
        }

        if (adjustmentsChanged(previousAdjustments, calculatedAdjustments)) {
            calculatedAdjustments.forEach(adj -> adj.setPriceInfo(priceInfo));
            priceInfo.resetAdjustments(calculatedAdjustments);
        }
    }

    private List<PriceAdjustment> applyCalculators(final Order order, final List<OrderCalculator> calculators,
            final PricingContext ctx) {
        final List<List<PriceAdjustment>> adjLists = new ArrayList<>(calculators.size());
        adjLists.addAll(calculators.stream()
                                .map(calculator -> calculator.priceOrder(order, ctx))
                                .collect(Collectors.toList()));
        return PriceUtils.mergeLists(adjLists);
    }

    public void setOrderCalculators(List<OrderCalculator> orderCalculators) {
        this.orderCalculators = orderCalculators;
    }
}
