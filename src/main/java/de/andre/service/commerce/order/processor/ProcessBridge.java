package de.andre.service.commerce.order.processor;

import org.springframework.validation.Errors;

import java.util.Map;

public interface ProcessBridge<P extends Processor, C extends BaseContext> {
    C createContext(Map<String, ?> params);

    ProcessorResult invokeProcessor(P processor, C ctx, Errors result);
}
