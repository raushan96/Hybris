package de.andre.service.commerce.order.price;

import de.andre.entity.order.Order;

public interface PricingEngine {
    void priceOrder(Order order, PricingContext ctx);
}
