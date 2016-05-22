package de.andre.repository.catalog;

import de.andre.entity.catalog.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, String> {
//    @Query("select new de.andre.entity.dto.PricedProductDTO(pr.dcsProduct.id, pr.listPrice, pr.priceList.id) from DcsPrice pr where pr.dcsProduct in :products")
//    List<PricedProductDTO> getProductsPrices(@Param("products") List<DcsProduct> products);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("select pr from Price pr where pr.product.id = :productId and pr.priceList.id = :priceListId")
    Price listPrice(@Param("productId") String productId, @Param("priceListId") String priceListId);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("select pr from Price pr where pr.product.id in :prdIds and pr.priceList.id = :priceListId")
    List<Price> pricesForProducts(@Param("prdIds") Collection<String> productIds, @Param("priceListId") String priceListId);
}
