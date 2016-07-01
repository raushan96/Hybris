package de.andre.repository.order;

import de.andre.entity.order.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
}
