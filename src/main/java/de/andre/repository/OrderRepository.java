package de.andre.repository;

import de.andre.entity.core.DpsUser;
import de.andre.entity.order.DcsppOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by andreika on 5/2/2015.
 */
public interface OrderRepository extends JpaRepository<DcsppOrder, Integer> {
	@Query("select distinct o from DcsppOrder o left join fetch o.commerceItems left join fetch o.shippingGroups left join fetch o.paymentGroups where o.dpsUser = :dpsUser and o.state = 0")
	List<DcsppOrder> getCurrentOrdersByUser(@Param("dpsUser") DpsUser dpsUser);
}
