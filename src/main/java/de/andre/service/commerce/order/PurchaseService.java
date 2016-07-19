package de.andre.service.commerce.order;

import de.andre.entity.dto.PaymentForm;
import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.CreditCard;
import de.andre.entity.order.Order;
import de.andre.entity.order.PaymentGroup;
import de.andre.service.commerce.order.price.AdjustmentsPersister;
import de.andre.service.commerce.order.price.RepriceEngine;
import de.andre.service.commerce.order.processor.ProcessChainExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static de.andre.service.commerce.order.processor.PipelineConstants.*;
import static java.util.Collections.*;

// purchase facade
public class PurchaseService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final CommerceItemsTools commerceItemsTools;
    private final ShippingGroupsTools shippingGroupsTools;
    private final RepriceEngine repriceEngine;
    private final AdjustmentsPersister adjustmentsPersister;
    private final ProcessChainExecutor chainExecutor;

    private OrderHolder orderHolder;

    public PurchaseService(final CommerceItemsTools commerceItemsTools, final ShippingGroupsTools shippingGroupsTools,
            final RepriceEngine repriceEngine,
            final AdjustmentsPersister adjustmentsPersister, final ProcessChainExecutor chainExecutor) {
        this.commerceItemsTools = commerceItemsTools;
        this.shippingGroupsTools = shippingGroupsTools;
        this.repriceEngine = repriceEngine;
        this.adjustmentsPersister = adjustmentsPersister;
        this.chainExecutor = chainExecutor;
    }

    @Transactional
    public Order currentOrder() {
        final Order order = orderHolder.currentOrder();
        synchronized (order) {
            repriceEngine.weakReprice(order);
        }
        return order;
    }

    @Transactional
    public void modifyOrderItem(final String productId, final Long quantity) {
        final Order order = orderHolder.currentOrder();
        logger.debug("Modifying {} product by {} quantity for {} order", productId, quantity, order.getId());

        synchronized (order) {
            final CommerceItem ci = commerceItemsTools.modifyItemQuantity(order, productId, quantity);
            shippingGroupsTools.modifyShippingGroupQuantity(ci, quantity, null);
            repriceEngine.repriceOrder(order);
            adjustmentsPersister.persistOrderPriceInfos(order);
        }
    }

    @Transactional
    public void deleteOrderItem(final Long commerceItemId) {
        final Order order = orderHolder.currentOrder();
        logger.debug("Deleting {} item for {} order", commerceItemId, order.getId());

        synchronized (order) {
            commerceItemsTools.removeItem(order, commerceItemId);
            repriceEngine.repriceOrder(order);
            adjustmentsPersister.persistOrderPriceInfos(order);
        }
    }

    @Transactional
    public void selectAddress(final Long addressId) {
        final Order order = orderHolder.currentOrder();

        synchronized (order) {
            shippingGroupsTools.modifyContactInfo(
                    shippingGroupsTools.defaultShippingGroup(order), addressId);
            repriceEngine.repriceOrder(order);
        }
    }

    @Transactional
    public void proceedOrder(final PaymentForm paymentForm) {
        final Order order = orderHolder.currentOrder();
        logger.debug("Starting submit process on {} order", order.getId());

        synchronized (order) {
            final PaymentGroup pg = order.getPaymentGroups().get(0);
            BeanUtils.copyProperties(paymentForm, pg);
            if (pg instanceof CreditCard) {
                ((CreditCard) pg).setExpiryDate(LocalDate.of(
                        paymentForm.getExpiryYear().getValue(),
                        paymentForm.getExpiryMonth().getValue(),
                        1)
                );
            }
            chainExecutor.executeOrderChain(CHAIN_ORDER_PROCESS, singletonMap(ORDER, order));
//            chainExecutor.executeOrderChain("validateOrder", singletonMap(ORDER, order));
            orderHolder.switchOrder();
        }
    }


    public void setOrderHolder(OrderHolder orderHolder) {
        this.orderHolder = orderHolder;
    }
}
