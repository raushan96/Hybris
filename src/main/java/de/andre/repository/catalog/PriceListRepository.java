package de.andre.repository.catalog;

import de.andre.entity.catalog.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceList, String> {
}
