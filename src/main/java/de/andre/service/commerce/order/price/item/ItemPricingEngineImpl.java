package de.andre.service.commerce.order.price.item;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.ItemPriceInfo;
import de.andre.entity.order.Order;
import de.andre.entity.order.PriceAdjustment;
import de.andre.service.commerce.order.price.PricingContext;
import de.andre.service.commerce.order.price.SkeletalPricingEngine;
import de.andre.utils.StreamUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPricingEngineImpl extends SkeletalPricingEngine {
    private List<ItemCalculator> preCalculators = Collections.emptyList();
    private List<ItemCalculator> postCalculators = Collections.emptyList();

    @Override
    protected void priceOrderInternal(final Order order, final PricingContext ctx) {
        final List<CommerceItem> cis = order.getCommerceItems();
        if (CollectionUtils.isEmpty(cis)) {
            logger.debug("Order {} does not contain any commerce items, returning", order.getId());
            return;
        }

        for (final CommerceItem ci : cis) {
            final ItemPriceInfo priceInfo = ci.getPriceInfo();
            Assert.notNull(priceInfo);
            applyCurrency(priceInfo, ctx);
            priceInfo.fillAmounts(BigDecimal.ZERO);

            final List<PriceAdjustment> previousAdjustments = priceInfo.getPriceAdjustments();

            final List<PriceAdjustment> preAdjustments = applyCalculators(ci, preCalculators, ctx);
            // promotions logic
            final List<PriceAdjustment> postAdjustments = applyCalculators(ci, postCalculators, ctx);

            final List<PriceAdjustment> calculatedAdjustments = StreamUtils.mergeLists(
                    Arrays.asList(preAdjustments, postAdjustments));
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

    private List<PriceAdjustment> applyCalculators(final CommerceItem item, final List<ItemCalculator> calculators,
            final PricingContext ctx) {
        final List<List<PriceAdjustment>> adjLists = new ArrayList<>(calculators.size());
        adjLists.addAll(calculators.stream()
                                .map(calculator -> calculator.priceItem(item, ctx))
                                .collect(Collectors.toList()));
        return StreamUtils.mergeLists(adjLists);
    }

    public void setPreCalculators(List<ItemCalculator> preCalculators) {
        this.preCalculators = preCalculators;
    }

    public void setPostCalculators(List<ItemCalculator> postCalculators) {
        this.postCalculators = postCalculators;
    }
}
