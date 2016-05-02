package de.andre.repository.catalog;

import de.andre.entity.catalog.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "select * from dcs_product prd where prd.product_id in" +
            "(select product_id from dcs_category_products where category_id in" +
            "(select category_id from dcs_category start with category_id = :catId connect by prior category_id = parent_cat_id))", nativeQuery = true)
    List<Product> getProductsWithAncestorsCat(@Param("catId") String catId);
}
