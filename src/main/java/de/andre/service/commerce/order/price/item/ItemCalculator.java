package de.andre.service.commerce.order.price.item;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.PricingContext;

import java.util.List;

public interface ItemCalculator {
    List<PriceAdjustment> priceItem(CommerceItem item, PricingContext ctx);
}
