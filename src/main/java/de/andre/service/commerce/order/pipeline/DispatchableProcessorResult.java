package de.andre.service.commerce.order.pipeline;

import org.springframework.util.Assert;

public class DispatchableProcessorResult extends ProcessorResult {
    private String dispatchChainName;

    public static DispatchableProcessorResult dispatchChainResult(
            final String processorName, final String dispatchProcessorName, final String dispatchChainName) {
        return new DispatchableProcessorResult(processorName, Result.CHAIN_DISPATCH, dispatchProcessorName,
                                               dispatchChainName);
    }

    public static DispatchableProcessorResult dispatchChainResult(
            final String processorName, final String dispatchChainName) {
        return new DispatchableProcessorResult(processorName, Result.CHAIN_DISPATCH, dispatchChainName);
    }

    private DispatchableProcessorResult(String processorName, Result action,
            String dispatchProcessorName, String dispatchChainName) {
        super(processorName, action, dispatchProcessorName);
        Assert.hasLength(dispatchChainName);
        this.dispatchChainName = dispatchChainName;
    }

    private DispatchableProcessorResult(String processorName, Result action, String dispatchChainName) {
        super(processorName, action);
        Assert.hasLength(dispatchChainName);
        this.dispatchChainName = dispatchChainName;
    }

    public String getDispatchChainName() {
        return dispatchChainName;
    }

    @Override
    public String toString() {
        return "DispatchableProcessorResult{" +
                "processorName='" + getProcessorName() + '\'' +
                ", action=" + getAction() + '\'' +
                ", dispatchChainName='" + dispatchChainName +
                "} ";
    }
}
