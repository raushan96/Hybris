package de.andre.service.commerce.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderUtils {
    private static final Logger logger = LoggerFactory.getLogger(OrderUtils.class);

    private OrderUtils() {
    }

    public static boolean orderActive(final Order order) {
        return order != null && order.getState() == OrderState.INCOMPLETE;
    }

    public static boolean orderPriced(final Order order) {
        if (order == null || order.getPriceInfo() == null)
            return false;
        for (final HardgoodShippingGroup hg : order.getHgShippingGroups()) {
            if (hg.getPriceInfo() == null)
                return false;
        }
        for (final CommerceItem ci : order.getCommerceItems()) {
            if (ci.getPriceInfo() == null)
                return false;
        }
        logger.debug("Order {} is already priced", order.getId());
        return true;
    }
}
