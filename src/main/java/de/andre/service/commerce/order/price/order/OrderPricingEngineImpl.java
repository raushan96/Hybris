package de.andre.service.commerce.order.price.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.price.Calculator;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.PricingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

public class OrderPricingEngineImpl implements PricingEngine {
    private static final Logger logger = LoggerFactory.getLogger(OrderPricingEngineImpl.class);

    private List<Calculator> orderCalculators = Collections.emptyList();

    @Override
    public void priceOrder(Order order, PricingContext pricingContext) {
        Assert.notNull(order);
        Assert.notNull(pricingContext);

        if (logger.isDebugEnabled()) {
            logger.debug("Starting shipping pricing on {} order, with {} context", order.getId(), pricingContext);
        }

        for (final Calculator orderCalculator : orderCalculators) {
            orderCalculator.priceOrderItems(order, pricingContext);
        }
    }

    public void setOrderCalculators(List<Calculator> orderCalculators) {
        this.orderCalculators = orderCalculators;
    }
}
