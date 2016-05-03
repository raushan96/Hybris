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


// oracle implementation, not available in mysql
//    @Query(value = "select * from dcs_product prd where prd.product_id in" +
//            "(select product_id from dcs_category_products where category_id in" +
//            "(select category_id from dcs_category start with category_id = :catId connect by prior category_id = parent_cat_id))", nativeQuery = true)


// select product_id from
//      (select rownum as rn, product_id from dcs_prd_anc_cats where category_id = ? and product_id in
//          (select p.product_id from dcs_product p, oe_product o where p.product_id = o.product_id and p.product_id in
//              (select pr.product_id from dcs_product_sites pr where pr.site_id = ?)
// and o.visible_in_store = 1 and ((start_date is null and end_date is null) or (start_date <= ? AND end_date is null) or (start_date is null and end_date >= ?) OR (start_date <= ? and end_date >= ?))))
// where rn >= ? and rn <= ?
}
