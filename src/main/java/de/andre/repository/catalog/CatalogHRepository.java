package de.andre.repository.catalog;

import de.andre.entity.catalog.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogHRepository extends JpaRepository<Catalog, String> {
}
