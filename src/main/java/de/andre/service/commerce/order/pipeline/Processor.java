package de.andre.service.commerce.order.pipeline;

import org.springframework.validation.Errors;

public interface Processor<T> {
    String getName();

    ProcResult process(ProcessContext<T> ctx, Errors result);
}
