package de.andre.service.commerce.order.pipeline;

import org.springframework.util.Assert;

public class ProcResult {
    private final String processorName;
    private final Result action;

    private String dispatchProcessorName;

    public static ProcResult successResult(final String processorName) {
        return new ProcResult(processorName, Result.PROCEED);
    }

    public static ProcResult dispatchProcessorResult(
            final String processorName, final String dispatchProcessorName) {
        return new ProcResult(processorName, Result.PROCESS_DISPATCH, dispatchProcessorName);
    }

    protected ProcResult(String processorName, Result action) {
        Assert.hasLength(processorName);
        Assert.notNull(action);

        this.processorName = processorName;
        this.action = action;
    }

    protected ProcResult(String processorName, Result action, String dispatchProcessorName) {
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
