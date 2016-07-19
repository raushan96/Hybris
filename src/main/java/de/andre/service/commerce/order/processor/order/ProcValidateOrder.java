package de.andre.service.commerce.order.processor.order;

import de.andre.entity.order.Order;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

public class ProcValidateOrder extends SkeletalOrderProcessor {
    public ProcValidateOrder(final String name, final MessageSource messageSource) {
        super(name, messageSource);
    }

    @Override
    public void processInternal(final OrderContext orderContext, final Errors result) {
        final Order order = orderContext.getOrder();

        if (order.getCommerceItems().isEmpty()) {
//            result.add(PipelineError.of("zero.ci"));
        }

        if (order.getHgShippingGroups().isEmpty()) {
//            result.addError(PipelineError.of("zero.sg"));
        }

        if (order.getPaymentGroups().isEmpty()) {
//            result.addError(PipelineError.of("zero.pg"));
        }

        validatePriceInfos(order, result);
    }

    private void validatePriceInfos(final Order order, final Errors result) {
//        if (order.getPriceInfo() == null) {
//            result.addError(PipelineError.of("order.pi"));
//        }
//
//        order.getCommerceItems().stream()
//                .filter(ci -> ci.getPriceInfo() == null)
//                .forEach(ci -> result.addError(PipelineError.of("ci.pi")));
//
//        order.getHgShippingGroups().stream()
//                .filter(sg -> sg.getPriceInfo() == null)
//                .forEach(sg -> result.addError(PipelineError.of("sg.pi")));
    }
}
