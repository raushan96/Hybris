package de.andre.repository.catalog;

import de.andre.entity.catalog.Catalog;
import de.andre.entity.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CatalogRepository extends JpaRepository<Catalog, String> {
    @Query("select ctl.allChildCategories as cats from Catalog ctl where cats.rootCategory = true " +
            "order by cats.displayName")
    Set<Category> rootCatalogCategories(@Param("ctlId") String ctlId);

    @Query(value = "select cat.id, cat.display_name from hc_category cat where cat.parent_cat_id = :catId", nativeQuery = true)
    List<String[]> getSubCategoriesIdsAndNames(@Param("catId") String catId);

/*    @Query("select cat from Category cat join fetch cat.childCategories where cat.categoryId = :catId")
    Category findCatWithChilds(@Param("catId") String catId);*/

    @Query(value = "select count(*) from dcs_category where parent_cat_id = :catId", nativeQuery = true)
    Integer getChildCatsCount(@Param("catId") String catId);

    @Query("select cat.parentCategory from Category cat where cat.id = :catId")
    Category categoryParent(@Param("catId") String catId);
}
