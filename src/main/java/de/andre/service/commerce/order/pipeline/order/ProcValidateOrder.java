package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import org.springframework.validation.Errors;

public class ProcValidateOrder extends SkeletalOrderProcessor {
    public ProcValidateOrder(final String name) {
        super(name);
    }

    @Override
    public void processInternal(final ProcessContext<Order> orderContext, final Errors result) {
        final Order order = orderContext.getTarget();

        if (order.getCommerceItems().isEmpty()) {
            result.reject("zero.ci");
        }

        if (order.getHgShippingGroups().isEmpty()) {
            result.reject("zero.sg");
        }

        if (order.getPaymentGroups().isEmpty()) {
            result.reject("zero.pg");
        }

        validatePriceInfos(order, result);
    }

    private void validatePriceInfos(final Order order, final Errors result) {
        if (order.getPriceInfo() == null) {
            result.reject("order.pi");
        }

        order.getCommerceItems().stream()
                .filter(ci -> ci.getPriceInfo() == null)
                .forEach(ci -> result.reject("ci.pi"));

        order.getHgShippingGroups().stream()
                .filter(sg -> sg.getPriceInfo() == null)
                .forEach(sg -> result.reject("sg.pi"));
    }
}
