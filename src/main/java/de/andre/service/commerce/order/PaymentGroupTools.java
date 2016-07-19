package de.andre.service.commerce.order;

import de.andre.entity.order.Order;
import de.andre.entity.order.PaymentGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class PaymentGroupTools {
    private static final Logger logger = LoggerFactory.getLogger(PaymentGroupTools.class);

    public PaymentGroup defaultPaymentGroup(final Order order) {
        Assert.notNull(order);
        return order.getPaymentGroups().get(0);
    }
}
