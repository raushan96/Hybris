package de.andre.repository.order;

import de.andre.entity.order.HardgoodShippingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HgShippingGroupRepository extends JpaRepository<HardgoodShippingGroup, Long> {
}
