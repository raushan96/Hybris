package de.andre.service.commerce.order.pipeline;

import org.springframework.validation.Errors;

public interface IComplexProcessChain<P> extends IProcessChain<P> {
    boolean invokeChain(String chainId, ProcessContext<P> ctx, Errors result);
}
