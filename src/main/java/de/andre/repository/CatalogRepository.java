package de.andre.repository;

import de.andre.entity.catalog.DcsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andreika on 4/21/2015.
 */
public interface CatalogRepository extends JpaRepository<DcsCategory, String> {
}
