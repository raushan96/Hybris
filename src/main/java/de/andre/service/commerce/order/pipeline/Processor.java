package de.andre.service.commerce.order.pipeline;

import org.springframework.validation.Errors;

public interface Processor<T> {
    String getName();

    ProcessorResult process(ProcessContext<T> ctx, Errors result);
}
