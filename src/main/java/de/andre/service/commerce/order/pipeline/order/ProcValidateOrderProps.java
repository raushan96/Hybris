package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class ProcValidateOrderProps extends SkeletalOrderProcessor {
    private final SpringValidatorAdapter hibernateValidator;

    protected ProcValidateOrderProps(final String name, final SpringValidatorAdapter hibernateValidator) {
        super(name);
        this.hibernateValidator = hibernateValidator;
    }

    @Override
    protected void processInternal(final ProcessContext<Order> orderContext, final Errors result) {
        final Order order = orderContext.getTarget();
        this.hibernateValidator.validate(order, result);
    }
}
