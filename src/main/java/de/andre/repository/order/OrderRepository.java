package de.andre.repository.order;

import de.andre.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select ord from Order ord where " +
            "ord.profile.id = :profileId and " +
            "ord.state = de.andre.entity.enums.OrderState.INCOMPLETE and " +
            "ord.submittedDate is null " +
            "order by ord.creationDate")
    List<Order> currentOrders(@Param("profileId") Long profileId);
}
