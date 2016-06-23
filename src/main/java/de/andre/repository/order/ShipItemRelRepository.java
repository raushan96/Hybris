package de.andre.repository.order;

import de.andre.entity.order.ShippingItemRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipItemRelRepository extends JpaRepository<ShippingItemRelationship, Long> {
}
