package de.andre.service.commerce.order;

import de.andre.entity.order.Order;
import de.andre.utils.ProfileHelper;

public class OrderHolder {
    private Order order;
    private String orderId;

    private final OrderManager orderManager;

    public OrderHolder(final OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public Order getOrder() {
        if (this.orderId == null) {
            order = orderManager.getProfileCurrentOrder(ProfileHelper.currentProfileId());
            if (null == order) {
//                order = orderManager.createOrder(accountTools.getCommerceProfile());
            }
        }
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
