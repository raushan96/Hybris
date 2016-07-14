package de.andre.service.commerce.order;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.Order;
import de.andre.service.commerce.order.price.AdjustmentsPersister;
import de.andre.service.commerce.order.price.RepriceEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

// purchase facade
public class PurchaseService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final CommerceItemsTools commerceItemsTools;
    private final ShippingGroupsTools shippingGroupsTools;
    private final RepriceEngine repriceEngine;
    private final AdjustmentsPersister adjustmentsPersister;

    private OrderHolder orderHolder;

    public PurchaseService(final CommerceItemsTools commerceItemsTools, final ShippingGroupsTools shippingGroupsTools, final RepriceEngine repriceEngine,
            final AdjustmentsPersister adjustmentsPersister) {
        this.commerceItemsTools = commerceItemsTools;
        this.shippingGroupsTools = shippingGroupsTools;
        this.repriceEngine = repriceEngine;
        this.adjustmentsPersister = adjustmentsPersister;
    }

    public Order currentOrder() {
        return orderHolder.currentOrder();
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

    public void setOrderHolder(OrderHolder orderHolder) {
        this.orderHolder = orderHolder;
    }
}
