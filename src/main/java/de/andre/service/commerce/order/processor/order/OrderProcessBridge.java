package de.andre.service.commerce.order.processor.order;

import de.andre.entity.order.Order;
import de.andre.multisite.SiteManager;
import de.andre.service.commerce.order.processor.PipelineConstants;
import de.andre.service.commerce.order.processor.ProcessBridge;
import de.andre.service.commerce.order.processor.ProcessorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import java.util.Map;

public class OrderProcessBridge implements ProcessBridge<OrderProcessor, OrderContext> {
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessBridge.class);

    @Override
    public OrderContext createContext(final Map<String, ?> params) {
        return new OrderContext(
                (Order) params.get(PipelineConstants.ORDER),
                LocaleContextHolder.getLocale(),
                SiteManager.getSite()
        );
    }

    @Override
    public ProcessorResult invokeProcessor(
            final OrderProcessor processor,
            final OrderContext ctx,
            final Errors result) {
        Assert.notNull(processor);
        logger.debug("Executing '{}' processor", processor.getName());
        return processor.process(ctx, result);
    }
}
