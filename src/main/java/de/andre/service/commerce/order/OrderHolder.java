package de.andre.service.commerce.order;

import de.andre.entity.order.Order;
import de.andre.utils.ProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class OrderHolder {
    private static final Logger logger = LoggerFactory.getLogger(OrderHolder.class);

    private Long orderId;

    private final OrderTools orderTools;

    private RequestOrder requestOrder;

    public OrderHolder(final OrderTools orderTools) {
        this.orderTools = orderTools;
    }

    @Transactional
    public Order currentOrder() {
        //request cache for order
        if (requestOrder.hasOrder()) {
            return requestOrder.getOrder();
        }

        Order currentOrder;
        if (this.orderId != null) {
            currentOrder = orderTools.loadOrder(orderId);
            if (!OrderUtils.orderActive(currentOrder)) {
                logger.debug("The order {} cannot be used as a shopping cart", orderId);
                currentOrder = reloadOrder();
                this.orderId = currentOrder.getId();
            }
        }
        else {
            currentOrder = reloadOrder();
            this.orderId = currentOrder.getId();
        }

        Assert.notNull(currentOrder, "Order cannot be null here");
        requestOrder.setOrder(currentOrder);
        return currentOrder;
    }

    @Transactional
    public Order switchOrder() {
        final Order nextOrder = reloadOrder();
        logger.debug("Switching order from '{}' to '{}'", this.orderId, nextOrder.getId());

        this.orderId = nextOrder.getId();
        requestOrder.setOrder(nextOrder);
        return nextOrder;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Order reloadOrder(final Order destOrder) {
        final List<Order> activeOrders = orderTools.getProfileCurrentOrders(ProfileUtils.currentId());
        if (CollectionUtils.isEmpty(activeOrders)) {
            return destOrder != null ? destOrder : orderTools.createOrder(ProfileUtils.currentId());
        }

        final Order reloadedOrder = destOrder != null ? destOrder : activeOrders.get(0);
        for (final Order srcOrder : activeOrders) {
            orderTools.mergeOrders(srcOrder, reloadedOrder);
            if (!reloadedOrder.equals(srcOrder)) {
                orderTools.removeOrder(srcOrder);
            }
        }

        return reloadedOrder;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Order reloadOrder() {
        return reloadOrder(null);
    }

    public boolean isResolved() {
        return this.orderId != null;
    }

    public void setRequestOrder(RequestOrder requestOrder) {
        this.requestOrder = requestOrder;
    }
}
