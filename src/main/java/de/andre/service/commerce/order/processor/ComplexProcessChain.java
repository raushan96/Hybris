package de.andre.service.commerce.order.processor;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;

import java.lang.reflect.Method;

public class ComplexProcessChain<P extends Processor, C extends BaseContext>
        extends ProcessChain<P, C>
        implements IComplexProcessChain<P, C> {
    private static final Method invokeChainMethod =
            ReflectionUtils.findMethod(IProcessChain.class, "processChain", Errors.class, BaseContext.class);

    private final ProcessChainExecutor chainExecutor;

    public ComplexProcessChain(final String chainId, final ProcessBridge<P, C> processBridge,
            final ProcessChainExecutor chainExecutor) {
        super(chainId, processBridge);
        this.chainExecutor = chainExecutor;
    }

    @Override
    public boolean invokeChain(final String chainId, final C ctx, final Errors result) {
        final IProcessChain<? extends Processor, ? extends BaseContext> dispatchedChain =
                this.chainExecutor.getRegisteredChains().get(chainId);
        Assert.notNull(dispatchedChain);
        return (Boolean) ReflectionUtils.invokeMethod(invokeChainMethod, dispatchedChain, result, ctx);
    }

    @Override
    protected boolean onProcessResult(
            final ProcessorResult processResult,
            final C ctx,
            final Errors result) {
        if (processResult instanceof DispatchableProcessorResult &&
                processResult.getAction() == ProcessorResult.Result.CHAIN_DISPATCH) {
            final DispatchableProcessorResult dispatchResult = (DispatchableProcessorResult) processResult;
            final String dispatchChain = dispatchResult.getDispatchChainName();
            logger.debug("Invoking '{}' chain from the '{}' complex chain", dispatchChain, this.getChainId());

            return invokeChain(dispatchChain, ctx, result);
        }
        return super.onProcessResult(processResult, ctx, result);
    }

    public ProcessChainExecutor getChainExecutor() {
        return chainExecutor;
    }
}
