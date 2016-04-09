package de.andre.repository;

import de.andre.entity.catalog.DcsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatalogRepository extends JpaRepository<DcsCategory, String> {
    @Query("select c from DcsCategory c where c.parentCategory.rootCategory = true")
    List<DcsCategory> getRootChildCategories();

    @Query(value = "select cat.category_id, cat.display_name from dcs_category cat where cat.parent_cat_id = :catId", nativeQuery = true)
    List<String[]> getSubCategoriesIdsAndNames(@Param("catId") String catId);

/*    @Query("select cat from DcsCategory cat join fetch cat.childCategories where cat.categoryId = :catId")
    DcsCategory findCatWithChilds(@Param("catId") String catId);*/

    @Query(value = "select count(*) from dcs_category where parent_cat_id = :catId", nativeQuery = true)
    Integer getChildCatsCount(@Param("catId") String catId);

    @Query("select cat.parentCategory from DcsCategory cat where cat.categoryId = :catId")
    DcsCategory getCategoryParent(@Param("catId") String catId);
}
