package de.andre.repository.order;

import de.andre.entity.order.CommerceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommerceItemRepository extends JpaRepository<CommerceItem, Long> {
}
