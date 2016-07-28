package de.andre.service.commerce.order.pipeline;

import de.andre.entity.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProcessChainExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessChainExecutor.class);

    private final Map<String, IProcessChain<?>> registeredChains = new HashMap<>();
    private Map<String, IProcessChain<Order>> orderChains = Collections.emptyMap();

    public void initialize() {
        this.registeredChains.putAll(orderChains);
    }

    public Errors executeOrderChain(final String pChainId, final ProcessContext<Order> ctx) {
        if (logger.isTraceEnabled()) {
            logger.trace("Executing chain for '{}' chainId and context '{}'", pChainId, ctx);
        }

        final IProcessChain<Order> chain = this.orderChains.get(pChainId);
        Assert.notNull(chain, "Chain is not defined for '" + pChainId + "' chainId");

        final Errors result = resultForChain(ctx);
        chain.processChain(ctx, result);

        if (logger.isTraceEnabled()) {
            logger.trace("Chain '{}' executed with result: {}", pChainId, result);
        }
        return result;
    }

    private Errors resultForChain(final ProcessContext<?> ctx) {
        return new BeanPropertyBindingResult(ctx.getTarget(), ctx.getTargetName());
    }

    public Map<String, IProcessChain<Order>> getOrderChains() {
        return orderChains;
    }

    public void setOrderChains(Map<String, IProcessChain<Order>> orderChains) {
        this.orderChains = orderChains;
    }

    public Map<String, IProcessChain<?>> getRegisteredChains() {
        return registeredChains;
    }
}
