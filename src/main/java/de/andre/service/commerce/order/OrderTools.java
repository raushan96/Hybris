package de.andre.service.commerce.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.enums.PaymentState;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.order.*;
import de.andre.entity.profile.Profile;
import de.andre.multisite.SiteManager;
import de.andre.repository.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class OrderTools {
    private static final Logger logger = LoggerFactory.getLogger(OrderTools.class);

    private final OrderRepository orderRepository;

    public OrderTools(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(final Profile profile) {
        final Order order = getOrderInstance();
        order.setNumber("hyb" + String.valueOf(new Random().nextInt() * 100));
        order.setProfile(profile);

        if (logger.isDebugEnabled()) {
            logger.debug("Created order instance with {} number", order.getNumber());
        }

//            DcsppAmountInfo amountInfo = new DcsppAmountInfo();
//            amountInfo.setCurrencyCode("EUR");
//            amountInfo.setType(AmountType.ORDER_PRICE_INFO);
//            order.setAmountInfo(amountInfo);
//
        final ShippingGroup shipGroup = new HardgoodShippingGroup();
        shipGroup.setShippingState(ShippingState.INITIAL);
//            order.addShippingGroup(shipGroup);

        final PaymentGroup payGroup = new CreditCard();
        payGroup.setState(PaymentState.INITIAL);
//            payGroup.setCurrencyCode("EUR");
//            order.addPaymentGroup(payGroup);

        return order;
    }

    private Order getOrderInstance() {
        final Order order = new Order();

        final LocalDateTime ts = LocalDateTime.now();
        order.setCreationDate(ts);
        order.setLastModifiedDate(ts);

        order.setState(OrderState.INCOMPLETE);
        order.setSiteId(SiteManager.getSiteId());

        return order;
    }

    @Transactional(readOnly = true)
    public Order getProfileCurrentOrder(final Long profileId) {
        final List<Order> orders = orderRepository.currentOrders(profileId);
        if (orders.size() == 0) {
            logger.info("No orders for profile {}", profileId);
            return null;
        } else if (orders.size() > 1) {
            logger.warn("More than one order for profile {}", profileId);
            return orders.get(0);
        } else {
            return orders.get(0);
        }
    }

    @Transactional
    public void persistOrder(final Order pOrder) {
        orderRepository.saveAndFlush(pOrder);
    }
}
