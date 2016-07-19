package de.andre.service.commerce.order.processor;

import org.springframework.util.Assert;

public class ProcessorResult {
    private final String processorName;
    private final Result action;

    private String dispatchProcessorName;

    public static ProcessorResult successResult(final String processorName) {
        return new ProcessorResult(processorName, Result.PROCEED);
    }

    public static ProcessorResult dispatchProcessorResult(
            final String processorName, final String dispatchProcessorName) {
        return new ProcessorResult(processorName, Result.PROCESS_DISPATCH, dispatchProcessorName);
    }

    protected ProcessorResult(String processorName, Result action) {
        Assert.hasLength(processorName);
        Assert.notNull(action);

        this.processorName = processorName;
        this.action = action;
    }

    protected ProcessorResult(String processorName, Result action, String dispatchProcessorName) {
        this(processorName, action);
        Assert.hasLength(dispatchProcessorName);
        this.dispatchProcessorName = dispatchProcessorName;
    }

    public String getProcessorName() {
        return processorName;
    }

    public String getDispatchProcessorName() {
        return dispatchProcessorName;
    }

    public Result getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "ProcessorResult{" +
                "processorName='" + processorName + '\'' +
                ", action=" + action +
                '}';
    }

    protected enum Result {
        PROCEED, PROCESS_DISPATCH, CHAIN_DISPATCH
    }
}
