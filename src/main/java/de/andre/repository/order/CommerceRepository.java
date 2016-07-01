package de.andre.repository.order;

import de.andre.repository.RepositoryAdapter;

public class CommerceRepository extends RepositoryAdapter {
    private final OrderRepository orderRepository;
    private final CommerceItemRepository commerceItemRepository;
    private final HgShippingGroupRepository hgShippingGroupRepository;
    private final PriceInfoRepository priceInfoRepository;

    public CommerceRepository(final OrderRepository orderRepository, final CommerceItemRepository commerceItemRepository,
            final HgShippingGroupRepository hgShippingGroupRepository, final PriceInfoRepository priceInfoRepository) {
        this.orderRepository = orderRepository;
        this.commerceItemRepository = commerceItemRepository;
        this.hgShippingGroupRepository = hgShippingGroupRepository;
        this.priceInfoRepository = priceInfoRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public CommerceItemRepository getCommerceItemRepository() {
        return commerceItemRepository;
    }

    public HgShippingGroupRepository getHgShippingGroupRepository() {
        return hgShippingGroupRepository;
    }

    public PriceInfoRepository getPriceInfoRepository() {
        return priceInfoRepository;
    }
}
