package de.andre.service.commerce.order;

import de.andre.entity.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class OrderManager {
    private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);

    private final OrderTools orderTools;

    public OrderManager(final OrderTools orderTools) {
        this.orderTools = orderTools;
    }

    @Transactional
    public void persistOrder(final Order pOrder) {
        orderTools.persistOrder(pOrder);
    }

//    @Transactional
//    public Order createOrder(final Profile profile) {
//        return orderTools.getOrderInstance(profile);
//    }
}
