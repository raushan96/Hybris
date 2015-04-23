package de.andre.repository;

import de.andre.entity.catalog.DcsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by andreika on 4/21/2015.
 */
public interface ProductRepository extends JpaRepository<DcsProduct, Integer> {
	@Query(value = "select * from dcs_product prd where prd.product_id in (select product_id from dcs_category_products where category_id in (select category_id from dcs_category start with category_id = :catId connect by prior category_id = parent_cat_id))", nativeQuery = true)
	List<DcsProduct> getProductsWithAncestorsCat(@Param("catId") String catId);
}
