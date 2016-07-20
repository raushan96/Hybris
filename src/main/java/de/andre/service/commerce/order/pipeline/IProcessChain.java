package de.andre.service.commerce.order.pipeline;

import org.springframework.validation.Errors;

public interface IProcessChain<P> {
    String getChainId();

    boolean processChain(ProcessContext<P> ctx, Errors result);

    boolean hasProcessor(String processorName);

    Processor<P> getProcessor(String processorName);

    int processorPosition(String processorName);
}
