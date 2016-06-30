package de.andre.service.commerce.order.price.shipping;

import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.Calculator;
import de.andre.service.commerce.order.price.PricingContext;

import java.util.List;

public interface ShippingCalculator extends Calculator {
    List<PriceAdjustment> priceShipping(HardgoodShippingGroup shippingGroup, PricingContext ctx);
}
