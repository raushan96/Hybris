package de.andre.service.commerce.order.price.order;

import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.Calculator;
import de.andre.service.commerce.order.price.PricingContext;

import java.util.List;

public interface OrderCalculator extends Calculator {
    List<PriceAdjustment> priceOrder(Order order, PricingContext ctx);
}
