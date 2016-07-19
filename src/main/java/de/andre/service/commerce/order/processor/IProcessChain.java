package de.andre.service.commerce.order.processor;

import org.springframework.validation.Errors;

public interface IProcessChain<P extends Processor, C extends BaseContext> {
    String getChainId();

    ProcessBridge<P, C> getProcessBridge();

    boolean processChain(C ctx, Errors result);

    boolean hasProcessor(String processorName);

    P getProcessor(String processorName);

    int processorPosition(String processorName);
}
