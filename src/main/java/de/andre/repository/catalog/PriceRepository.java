package de.andre.repository.catalog;

import de.andre.entity.catalog.Price;
import de.andre.entity.dto.PricedProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
//    @Query("select new de.andre.entity.dto.PricedProductDTO(pr.dcsProduct.id, pr.listPrice, pr.priceList.id) from DcsPrice pr where pr.dcsProduct in :products")
//    List<PricedProductDTO> getProductsPrices(@Param("products") List<DcsProduct> products);
}
