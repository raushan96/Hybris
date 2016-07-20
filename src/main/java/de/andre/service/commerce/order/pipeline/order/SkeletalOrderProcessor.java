package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import de.andre.service.commerce.order.pipeline.Processor;
import de.andre.service.commerce.order.pipeline.ProcessorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public abstract class SkeletalOrderProcessor implements Processor<Order> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String name;
    private boolean enabled = true;

    protected SkeletalOrderProcessor(final String name) {
        Assert.hasText(name);
        this.name = name;
    }

    @Override
    public ProcessorResult process(final ProcessContext<Order> orderContext, final Errors result) {
        if (!isEnabled()) {
            logger.debug("Processor '{}' is not enabled", this.getName());
            return ProcessorResult.successResult(this.getName());
        }

        logger.debug("Starting '{}' processor", this.getName());
        processInternal(orderContext, result);
        logger.debug("Finished '{}' processor with result: '{}'", this.getName(), result.hasErrors());

        return ProcessorResult.successResult(getName());
    }

    protected abstract void processInternal(final ProcessContext<Order> orderContext, final Errors result);

    @Override
    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
