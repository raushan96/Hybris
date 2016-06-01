package de.andre.service.commerce.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.order.Order;

public class OrderUtils {
    private OrderUtils() {
    }

    public static boolean orderActive(final Order order) {
        return order != null && order.getState() == OrderState.INCOMPLETE;
    }
}
