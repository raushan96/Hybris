package de.andre.repository;

import de.andre.entity.order.DcsppShipGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andreika on 5/3/2015.
 */
public interface ShippingGroupRepository extends JpaRepository<DcsppShipGroup, Integer> {
}
