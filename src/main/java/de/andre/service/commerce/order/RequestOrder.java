package de.andre.service.commerce.order;

import de.andre.entity.order.Order;

public class RequestOrder {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean hasOrder() {
        return this.order != null;
    }
}
