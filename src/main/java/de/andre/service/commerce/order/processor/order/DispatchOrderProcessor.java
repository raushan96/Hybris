package de.andre.service.commerce.order.processor.order;

import de.andre.service.commerce.order.processor.DispatchableProcessorResult;
import de.andre.service.commerce.order.processor.ProcessorResult;
import org.springframework.validation.Errors;

public class DispatchOrderProcessor implements OrderProcessor {
    @Override
    public ProcessorResult process(OrderContext orderContext, Errors result) {
        return DispatchableProcessorResult.dispatchChainResult(getName(), "validateOrder");
    }

    @Override
    public String getName() {
        return "dispatchProcessor";
    }
}
