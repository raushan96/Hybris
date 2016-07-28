package de.andre.service.commerce.order.pipeline;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;

import java.lang.reflect.Method;

public class ComplexProcessChain<P> extends ProcessChain<P>
        implements IComplexProcessChain<P> {
    private static final Method invokeChainMethod =
            ReflectionUtils.findMethod(IProcessChain.class, "processChain", ProcessContext.class, Errors.class);

    private final ProcessChainExecutor chainExecutor;

    public ComplexProcessChain(final String chainId, final ProcessChainExecutor chainExecutor) {
        super(chainId);
        this.chainExecutor = chainExecutor;
    }

    @Override
    public boolean invokeChain(final String chainId, final ProcessContext<P> ctx, final Errors result) {
        final IProcessChain<?> dispatchedChain = this.chainExecutor.getRegisteredChains().get(chainId);
        Assert.notNull(dispatchedChain);
        return (Boolean) ReflectionUtils.invokeMethod(invokeChainMethod, dispatchedChain, result, ctx);
    }

    @Override
    protected boolean onProcessResult(
            final ProcResult processResult,
            final ProcessContext<P> ctx,
            final Errors result) {
        if (processResult instanceof DispatchableProcResult &&
                processResult.getAction() == ProcResult.Result.CHAIN_DISPATCH) {
            final DispatchableProcResult dispatchResult = (DispatchableProcResult) processResult;
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
