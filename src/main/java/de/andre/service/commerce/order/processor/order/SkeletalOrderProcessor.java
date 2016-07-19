package de.andre.service.commerce.order.processor.order;

import de.andre.service.commerce.order.processor.ProcessorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public abstract class SkeletalOrderProcessor implements OrderProcessor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String name;
    protected final MessageSource messageSource;

    private boolean enabled = true;

    protected SkeletalOrderProcessor(final String name, final MessageSource messageSource) {
        Assert.hasText(name);
        Assert.notNull(messageSource);

        this.messageSource = messageSource;
        this.name = name;
    }

    @Override
    public ProcessorResult process(final OrderContext orderContext, final Errors result) {
        if (!isEnabled()) {
            logger.debug("Processor '{}' is not enabled", this.getName());
            return ProcessorResult.successResult(this.getName());
        }

        logger.debug("Starting '{}' processor", this.getName());
        processInternal(orderContext, result);
        logger.debug("Finished '{}' processor with result: {}", this.getName(), result);

        return ProcessorResult.successResult(getName());
    }

    protected abstract void processInternal(final OrderContext orderContext, final Errors result);

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
