package de.andre.service.commerce.order.price.shipping;

import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.entity.order.ShippingPriceInfo;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletonPricingEngine;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ShippingFixedPriceCalculator extends SkeletonPricingEngine {
    private BigDecimal fixedAmount = BigDecimal.ZERO;

    @Override
    protected void priceOrderInternal(final Order order, final PricingContext ctx) {
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();
        if (CollectionUtils.isEmpty(hgs)) {
            logger.error("Order {} does not contain any shipping groups, returning", order.getId());
            return;
        }

        for (final HardgoodShippingGroup hg : hgs) {
            final ShippingPriceInfo priceInfo = hg.getPriceInfo();
            Assert.notNull(priceInfo);

            applyCurrency(priceInfo, ctx);
            processFixedShippingAmount(getFixedAmount(), priceInfo);
        }
    }

    private void processFixedShippingAmount(final BigDecimal fixedAmount, final ShippingPriceInfo priceInfo) {
        if (!Objects.equals(priceInfo.getAmount(), fixedAmount)) {
            priceInfo.fillAmounts(fixedAmount);

            final PriceAdjustment adj = new PriceAdjustment();
            adj.setQtyAdjusted(1L);
            adj.setAdjustment(fixedAmount);
            adj.setAdjDescription("Fixed shipping price");
            priceInfo.priceAdjustmentsInternal().add(adj);
        }
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }
}
