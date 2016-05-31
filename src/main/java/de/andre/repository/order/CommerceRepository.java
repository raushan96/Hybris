package de.andre.repository.order;

import de.andre.repository.RepositoryAdapter;

public class CommerceRepository extends RepositoryAdapter {
    private final OrderRepository orderRepository;
    private final CommerceItemRepository commerceItemRepository;
    private final HgShippingGroupRepository hgShippingGroupRepository;

    public CommerceRepository(final OrderRepository orderRepository, CommerceItemRepository commerceItemRepository, HgShippingGroupRepository hgShippingGroupRepository) {
        this.orderRepository = orderRepository;
        this.commerceItemRepository = commerceItemRepository;
        this.hgShippingGroupRepository = hgShippingGroupRepository;
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
}
