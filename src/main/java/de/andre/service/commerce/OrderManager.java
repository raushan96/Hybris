package de.andre.service.commerce;

import de.andre.entity.order.Order;
import de.andre.entity.profile.Profile;
import de.andre.repository.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public class OrderManager {
    private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);

    private final OrderRepository orderRepository;
    private final OrderTools orderTools;

    public OrderManager(final OrderRepository orderRepository, final OrderTools orderTools) {
        this.orderRepository = orderRepository;
        this.orderTools = orderTools;
    }

    @Transactional(readOnly = true)
    public Order getProfileCurrentOrder(final Profile profile) {
//        final List<Order> orders = orderRepository.getCurrentOrdersByProfile(profile);
        final List<Order> orders = Collections.emptyList();
        if (orders.size() == 0) {
            logger.info("No orders for Profile {}", profile.getId());
            return null;
        } else if (orders.size() > 1) {
            logger.warn("More than one order for Profile {}", profile.getId());
            return orders.get(0);
        } else {
            return orders.get(0);
        }
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
