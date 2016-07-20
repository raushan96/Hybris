package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.pipeline.DispatchableProcessorResult;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import de.andre.service.commerce.order.pipeline.Processor;
import de.andre.service.commerce.order.pipeline.ProcessorResult;
import org.springframework.validation.Errors;

public class DispatchOrderProcessor implements Processor<Order> {
    @Override
    public ProcessorResult process(ProcessContext<Order> orderContext, Errors result) {
        return DispatchableProcessorResult.dispatchChainResult(getName(), "validateOrder");
    }

    @Override
    public String getName() {
        return "dispatchProcessor";
    }
}
