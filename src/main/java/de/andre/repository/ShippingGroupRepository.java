package de.andre.repository;

import de.andre.entity.order.DcsppShipGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingGroupRepository extends JpaRepository<DcsppShipGroup, Integer> {
}
