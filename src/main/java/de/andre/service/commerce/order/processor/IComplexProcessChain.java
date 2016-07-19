package de.andre.service.commerce.order.processor;

import org.springframework.validation.Errors;

public interface IComplexProcessChain<P extends Processor, C extends BaseContext>
        extends IProcessChain<P, C> {
    boolean invokeChain(String chainId, C ctx, Errors result);
}
