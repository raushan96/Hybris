package de.andre.repository.order;

import de.andre.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select ord from Order ord " +
                "left join fetch ord.commerceItems cis " +
                "left join fetch cis.shippingItemRelationships " +
                "left join fetch ord.hgShippingGroups " +
                "left join fetch ord.paymentGroups " +
            "where ord.profile.id = :profileId and " +
            "ord.state = de.andre.entity.enums.OrderState.INCOMPLETE and " +
            "ord.siteId = :siteId " +
            "order by ord.lastModifiedDate")
    List<Order> currentOrders(@Param("profileId") Long profileId, @Param("siteId") String siteId);

    @Query("select ord from Order ord " +
            "left join fetch ord.commerceItems cis " +
            "left join fetch cis.shippingItemRelationships " +
            "left join fetch ord.hgShippingGroups " +
            "left join fetch ord.paymentGroups " +
            "where ord.id = :orderId")
    Order fetchOrder(@Param("orderId") Long orderId);
}
