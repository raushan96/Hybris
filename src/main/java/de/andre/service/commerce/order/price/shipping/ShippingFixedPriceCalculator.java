package de.andre.service.commerce.order.price.shipping;

import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.PriceAdjustment;
import de.andre.entity.order.ShippingPriceInfo;
import de.andre.service.commerce.order.price.PricingContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ShippingFixedPriceCalculator implements ShippingCalculator {
    private BigDecimal fixedAmount = BigDecimal.ZERO;

    @Override
    public List<PriceAdjustment> priceShipping(final HardgoodShippingGroup shippingGroup, final PricingContext ctx) {
        final ShippingPriceInfo priceInfo = shippingGroup.getPriceInfo();

        return Collections.singletonList(processFixedShippingAmount(getFixedAmount(), priceInfo));
    }

    private PriceAdjustment processFixedShippingAmount(final BigDecimal fixedAmount, final ShippingPriceInfo priceInfo) {
        priceInfo.setRawAmount(fixedAmount);
        priceInfo.setAmount(fixedAmount);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(1L);
        adj.setAdjustment(fixedAmount);
        adj.setAdjDescription("Fixed shipping price");
        return adj;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }
}
