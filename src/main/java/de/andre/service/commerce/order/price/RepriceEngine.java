package de.andre.service.commerce.order.price;

import de.andre.entity.order.Order;

public interface RepriceEngine extends PricingEngine {
    void repriceOrder(Order order);
}
