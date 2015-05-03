package de.andre.repository;

import de.andre.entity.order.DcsppPayGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andreika on 5/3/2015.
 */
public interface PaymentGroupRepository extends JpaRepository<DcsppPayGroup, Integer> {
}
