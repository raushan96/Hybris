package de.andre.service.commerce.order.processor.order;

import de.andre.service.commerce.order.processor.Processor;
import de.andre.service.commerce.order.processor.ProcessorResult;
import org.springframework.validation.Errors;

public interface OrderProcessor extends Processor {
    ProcessorResult process(OrderContext orderContext, Errors result);
}
