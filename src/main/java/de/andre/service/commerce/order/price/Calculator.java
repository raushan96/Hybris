package de.andre.service.commerce.order.price;

import de.andre.entity.order.Order;

public interface Calculator {
    void priceOrderItems(Order order, PricingContext ctx);
}
