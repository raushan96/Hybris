package de.andre.repository;

import de.andre.entity.order.DcsppPayGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentGroupRepository extends JpaRepository<DcsppPayGroup, Integer> {
}
