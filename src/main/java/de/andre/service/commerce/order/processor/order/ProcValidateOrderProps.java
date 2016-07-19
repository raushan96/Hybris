package de.andre.service.commerce.order.processor.order;

import de.andre.entity.order.Order;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class ProcValidateOrderProps extends SkeletalOrderProcessor {
    private final SpringValidatorAdapter hibernateValidator;

    protected ProcValidateOrderProps(final String name, final MessageSource messageSource,
            final SpringValidatorAdapter hibernateValidator) {
        super(name, messageSource);
        this.hibernateValidator = hibernateValidator;
    }

    @Override
    protected void processInternal(final OrderContext orderContext, final Errors result) {
        final Order order = orderContext.getOrder();
        this.hibernateValidator.validate(order, result);
    }
}
