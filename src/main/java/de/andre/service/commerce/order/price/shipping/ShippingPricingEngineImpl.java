package de.andre.service.commerce.order.price.shipping;

import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.entity.order.ShippingPriceInfo;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletalPricingEngine;
import de.andre.utils.StreamUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShippingPricingEngineImpl extends SkeletalPricingEngine {
    private List<ShippingCalculator> shippingCalculators = Collections.emptyList();

    @Override
    protected void priceOrderInternal(final Order order, final PricingContext ctx) {
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();
        if (CollectionUtils.isEmpty(hgs)) {
            logger.debug("Order {} does not contain any hardgood shipping groups, returning", order.getId());
            return;
        }

        for (final HardgoodShippingGroup hg : hgs) {
            final ShippingPriceInfo priceInfo = hg.getPriceInfo();
            Assert.notNull(priceInfo);
            applyCurrency(priceInfo, ctx);
            priceInfo.fillAmounts(BigDecimal.ZERO);

            final List<PriceAdjustment> previousAdjustments = priceInfo.getPriceAdjustments();

            final List<PriceAdjustment> calculatedAdjustments = applyCalculators(hg, shippingCalculators, ctx);
            if (logger.isDebugEnabled()) {
                logger.debug("Final adjustments calculated list: {}",
                             StringUtils.collectionToCommaDelimitedString(calculatedAdjustments));
            }

            if (adjustmentsChanged(previousAdjustments, calculatedAdjustments)) {
                calculatedAdjustments.forEach(adj -> adj.setPriceInfo(priceInfo));
                priceInfo.resetAdjustments(calculatedAdjustments);
            }
        }
    }

    private List<PriceAdjustment> applyCalculators(final HardgoodShippingGroup shippingGroup, final List<ShippingCalculator> calculators,
            final PricingContext ctx) {
        final List<List<PriceAdjustment>> adjLists = new ArrayList<>(calculators.size());
        adjLists.addAll(calculators.stream()
                                .map(calculator -> calculator.priceShipping(shippingGroup, ctx))
                                .collect(Collectors.toList()));
        return StreamUtils.mergeLists(adjLists);
    }

    public void setShippingCalculators(List<ShippingCalculator> shippingCalculators) {
        this.shippingCalculators = shippingCalculators;
    }
}
