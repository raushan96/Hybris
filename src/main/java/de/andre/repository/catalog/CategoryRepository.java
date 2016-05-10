package de.andre.repository.catalog;

import de.andre.entity.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("select cat from Category cat join Catalog ctl on ctl.id = :ctlId " +
            "left join fetch cat.childCategories " +
            "where cat.rootCategory = true")
    Category rootCatalogCategory(@Param("ctlId") String ctlId);
}