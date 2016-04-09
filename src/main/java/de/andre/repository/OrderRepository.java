package de.andre.repository;

import de.andre.entity.order.DcsppOrder;
import de.andre.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<DcsppOrder, Integer> {
    @Query("select distinct o from DcsppOrder o left join fetch o.commerceItems left join fetch o.shippingGroups left join fetch o.paymentGroups where o.profile = :profile and o.state = 0")
    List<DcsppOrder> getCurrentOrdersByProfile(@Param("Profile") Profile profile);
}
