package de.andre.repository;

import de.andre.entity.order.DcsppItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommerceItemRepository extends JpaRepository<DcsppItem, Integer> {
}
