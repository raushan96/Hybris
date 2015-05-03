package de.andre.repository;

import de.andre.entity.order.DcsppItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andreika on 5/3/2015.
 */
public interface CommerceItemRepository extends JpaRepository<DcsppItem, Integer> {
}
