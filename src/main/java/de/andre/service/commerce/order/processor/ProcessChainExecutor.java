package de.andre.service.commerce.order.processor;

import de.andre.service.commerce.order.processor.order.OrderContext;
import de.andre.service.commerce.order.processor.order.OrderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProcessChainExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessChainExecutor.class);

    private Map<String, IProcessChain<OrderProcessor, OrderContext>> orderChains = Collections.emptyMap();
    private Map<String, IProcessChain<? extends Processor, ? extends BaseContext>> registeredChains = new HashMap<>();

    public void initialize() {
        this.registeredChains.putAll(orderChains);
    }

    public void executeOrderChain(final String pChainId, final Map<String, ?> params) {
        logger.debug("Executing chain for '{}' chainId", pChainId);

        final IProcessChain<OrderProcessor, OrderContext> chain = this.orderChains.get(pChainId);
        Assert.notNull(chain, "Chain is not defined for '" + pChainId + "' chainId");

        final boolean success = chain.processChain(
                chain.getProcessBridge().createContext(params), resultForChain(pChainId));
    }

    private Errors resultForChain(final String pChainId) {
        return null;
    }

    public Map<String, IProcessChain<OrderProcessor, OrderContext>> getOrderChains() {
        return orderChains;
    }

    public void setOrderChains(
            Map<String, IProcessChain<OrderProcessor, OrderContext>> orderChains) {
        this.orderChains = orderChains;
    }

    public Map<String, IProcessChain<? extends Processor, ? extends BaseContext>> getRegisteredChains() {
        return registeredChains;
    }
}
