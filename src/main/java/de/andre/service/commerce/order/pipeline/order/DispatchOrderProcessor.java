package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.Order;
import de.andre.service.commerce.order.pipeline.DispatchableProcResult;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import de.andre.service.commerce.order.pipeline.Processor;
import de.andre.service.commerce.order.pipeline.ProcResult;
import org.springframework.validation.Errors;

public class DispatchOrderProcessor implements Processor<Order> {
    @Override
    public ProcResult process(ProcessContext<Order> orderContext, Errors result) {
        return DispatchableProcResult.dispatchChainResult(getName(), "validateOrder");
    }

    @Override
    public String getName() {
        return "dispatchProcessor";
    }
}
