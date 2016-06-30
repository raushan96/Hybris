package de.andre.service.commerce.order.price.item;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.ItemPriceInfo;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.price.PriceTools;
import de.andre.service.price.ProductPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemPriceListCalculator implements ItemCalculator {
    private final PriceTools priceTools;

    public ItemPriceListCalculator(final PriceTools priceTools) {
        this.priceTools = priceTools;
    }

    @Override
    public List<PriceAdjustment> priceItem(final CommerceItem item, final PricingContext ctx) {
        final ItemPriceInfo priceInfo = item.getPriceInfo();
        final ProductPrice prdPrice = priceTools.priceProduct(item.getProduct().getId());

        final List<PriceAdjustment> adjustments = new ArrayList<>(2);
        adjustments.add(processBasePrice(prdPrice.getBasePrice(), priceInfo, item.getQuantity()));
        final PriceAdjustment salesAdjustment = processSalePrice(prdPrice.getSalePrice(), priceInfo, item.getQuantity());
        if (salesAdjustment != null) {
            adjustments.add(salesAdjustment);
        }

        return adjustments;
    }

    private PriceAdjustment processBasePrice(final BigDecimal basePrice, final ItemPriceInfo priceInfo,
            final Long pQuantity) {
        final BigDecimal mRawAmount = basePrice.multiply(BigDecimal.valueOf(pQuantity));
        priceInfo.setRawAmount(mRawAmount);
        priceInfo.setAmount(mRawAmount);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(pQuantity);
        adj.setAdjustment(mRawAmount);
        adj.setAdjDescription("Base price list");
        return adj;
    }

    private PriceAdjustment processSalePrice(final BigDecimal salePrice, final ItemPriceInfo priceInfo,
            final Long pQuantity) {
        if (BigDecimal.ZERO.compareTo(salePrice) > 0) {
            final BigDecimal mAmount = salePrice.multiply(BigDecimal.valueOf(pQuantity));
            priceInfo.setAmount(mAmount);

            final PriceAdjustment adj = new PriceAdjustment();
            adj.setQtyAdjusted(pQuantity);
            adj.setAdjustment(mAmount);
            adj.setAdjDescription("Sales price list");
            return adj;
        }
        return null;
    }
}
