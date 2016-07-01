package de.andre.service.commerce.order.price;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.PriceInfo;
import de.andre.repository.order.CommerceRepository;
import org.springframework.util.Assert;

public class AdjustmentsPersister {
    private final CommerceRepository commerceRepository;

    public AdjustmentsPersister(final CommerceRepository commerceRepository) {
        this.commerceRepository = commerceRepository;
    }

    public void persistOrderPriceInfos(final Order pOrder) {
        Assert.notNull(pOrder);
        persistPriceInfo(pOrder.getPriceInfo());
        for (final CommerceItem ci : pOrder.getCommerceItems()) {
            persistPriceInfo(ci.getPriceInfo());
        }

        for (final HardgoodShippingGroup hg : pOrder.getHgShippingGroups()) {
            persistPriceInfo(hg.getPriceInfo());
        }
    }

    public void persistPriceInfo(final PriceInfo priceInfo) {
        Assert.notNull(priceInfo);
        commerceRepository.getPriceInfoRepository().save(priceInfo);
    }

    public void persistPriceInfoAdjustments(final PriceInfo priceInfo) {
        Assert.notNull(priceInfo);
        commerceRepository.getEntityManager().persist(priceInfo.getPriceAdjustments());
    }
}
