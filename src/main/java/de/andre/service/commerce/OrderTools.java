package de.andre.service.commerce;

import de.andre.entity.enums.OrderState;
import de.andre.entity.order.Order;
import de.andre.entity.profile.Profile;
import de.andre.multisite.SiteManager;
import de.andre.repository.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
//            DcsppShipGroup shipGroup = new DcsppShipGroup();
//            shipGroup.setState(ShippingState.INITIAL);
//            order.addShippingGroup(shipGroup);
//
//            DcsppPayGroup payGroup = new DcsppPayGroup();
//            payGroup.setCurrencyCode("EUR");
//            payGroup.setState(PaymentStatus.INITIAL);
//            order.addPaymentGroup(payGroup);

        orderRepository.save(order);

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

    @Transactional
    public void persistOrder(final Order pOrder) {
        orderRepository.saveAndFlush(pOrder);
    }
}
