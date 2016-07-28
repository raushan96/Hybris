package de.andre.service.commerce.order.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

import java.util.*;

import static de.andre.service.commerce.order.pipeline.PipelineConstants.ERROR_GENERIC;

public class ProcessChain<P> implements IProcessChain<P> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String chainId;

    private boolean enabled = true;
    private boolean forceExecution = false;
    protected List<Processor<P>> processors = Collections.emptyList();

    protected ProcessChainValidator chainValidator = new DefaultChainValidator();

    public ProcessChain(final String chainId) {
        Assert.hasText(chainId);
        this.chainId = chainId;
    }

    @Override
    public boolean processChain(final ProcessContext<P> ctx, final Errors result) {
        if (!chainValidator.validateChain(this)) {
            return false;
        }

        if (result.hasErrors()) {
            logger.debug("Skipping '{}' chain because of existing errors", this.chainId);
            return false;
        }

        logger.debug("Invoking {} process chain with {} context", this.chainId, ctx);

        final Set<String> seenSoFar = new HashSet<>(this.processors.size());
        int currentPosition = 0;

        while (currentPosition < this.processors.size()) {
            final Processor<P> processor = this.processors.get(currentPosition);
            final String processorName = processor.getName();

            if (!seenSoFar.add(processorName)) {
                logger.warn("{} processor was already executed, possible circular reference", processorName);
                if (!isForceExecution()) {
                    return false;
                }
            }

            try {
                final ProcResult res = processor.process(ctx, result);
                Assert.notNull(res, "Result must be not null after processing!");

                if (result.hasErrors()) {
                    logger.debug("Cancelling '{}' chain because of errors during execution", this.chainId);
                    break;
                }
                else if (res.getAction() == ProcResult.Result.PROCEED) {
                    logger.debug("Processor {} returns proceed result", processorName);
                    currentPosition++;
                }
                else if (res.getAction() == ProcResult.Result.PROCESS_DISPATCH) {
                    logger.debug("Processor {} returns dispatch result, dispatching to {}",
                                 processorName, res.getDispatchProcessorName());
                    currentPosition = processorPosition(res.getDispatchProcessorName());
                }
                else {
                    if (onProcessResult(res, ctx, result)) {
                        currentPosition++;
                    }
                }
            } catch (final Exception ex) {
                logger.error("Unhandled exception during " + processorName + " processor execution", ex);
                result.reject(ERROR_GENERIC);
                return false;
            }
        }

        logger.debug("Finished executing {} chain", this.chainId);
        return true;
    }

    protected boolean onProcessResult(
            final ProcResult processResult,
            final ProcessContext<P> ctx,
            final Errors result) {
        logger.warn("Unsupported result operation '{}' received, proceeding into next processor" +
                            "Consider using advanced chain classes for handling", processResult.getAction());
        return true;
    }

    @Override
    public boolean hasProcessor(final String processorName) {
        return this.processors.stream()
                .filter(proc -> proc.getName().equals(processorName))
                .findAny()
                .isPresent();
    }

    @Override
    public Processor<P> getProcessor(final String processorName) {
        return this.processors.stream()
                .filter(proc -> proc.getName().equals(processorName))
                .findAny()
                .orElse(null);
    }

    @Override
    public int processorPosition(final String processorName) {
        for (int i = 0; i < this.processors.size(); i++) {
            if (Objects.equals(this.processors.get(i).getName(), processorName)) {
                return i;
            }
        }
        throw new IllegalArgumentException(
                String.format("No processors were found for %s chainId in %s chain", processorName, this.chainId));
    }

    @Override
    public String getChainId() {
        return this.chainId;
    }

    public List<Processor<P>> getProcessors() {
        return processors;
    }

    public void setProcessors(List<Processor<P>> processors) {
        this.processors = processors;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isForceExecution() {
        return forceExecution;
    }

    public void setForceExecution(boolean forceExecution) {
        this.forceExecution = forceExecution;
    }

    public void setChainValidator(ProcessChainValidator chainValidator) {
        this.chainValidator = chainValidator;
    }

    public interface ProcessChainValidator {
        boolean validateChain(ProcessChain processChain);
    }

    private static class DefaultChainValidator implements ProcessChainValidator {
        @Override
        public boolean validateChain(final ProcessChain processChain) {
            if (!processChain.isEnabled()) {
                processChain.logger.debug("Processor chain {} is not enabled, returning", processChain.chainId);
                return false;
            }

            if (CollectionUtils.isEmpty(processChain.processors)) {
                processChain.logger.debug("Processor chain {} is empty, returning", processChain.chainId);
                return false;
            }

            return true;
        }
    }
}