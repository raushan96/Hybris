package de.andre.service.commerce.order.price.item;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.ItemPriceInfo;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.price.PriceTools;
import de.andre.service.price.ProductPrice;

import java.math.BigDecimal;
import java.util.Arrays;
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

        return Arrays.asList(
                processBasePrice(prdPrice.getBasePrice(), priceInfo, item.getQuantity()),
                processSalePrice(prdPrice.getSalePrice(), priceInfo, item.getQuantity())
        );
    }

    private PriceAdjustment processBasePrice(final BigDecimal basePrice, final ItemPriceInfo priceInfo, final Long pQuantity) {
        final BigDecimal mRawAmount = basePrice.multiply(BigDecimal.valueOf(pQuantity));
        priceInfo.setRawAmount(mRawAmount);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(pQuantity);
        adj.setAdjustment(mRawAmount);
        adj.setAdjDescription("Base price list");
        return adj;
    }

    private PriceAdjustment processSalePrice(final BigDecimal salePrice, final ItemPriceInfo priceInfo, final Long pQuantity) {
        final BigDecimal mAmount = salePrice.multiply(BigDecimal.valueOf(pQuantity));
        priceInfo.setAmount(mAmount);

        final PriceAdjustment adj = new PriceAdjustment();
        adj.setQtyAdjusted(pQuantity);
        adj.setAdjustment(mAmount);
        adj.setAdjDescription("Sales price list");
        return adj;
    }
}
