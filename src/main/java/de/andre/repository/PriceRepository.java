package de.andre.repository;

import de.andre.entity.catalog.DcsPrice;
import de.andre.entity.catalog.DcsProduct;
import de.andre.entity.dto.PricedProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by andreika on 4/25/2015.
 */
public interface PriceRepository extends JpaRepository<DcsPrice, Integer> {
	@Query("select new de.andre.entity.dto.PricedProductDTO(pr.dcsProduct.id, pr.listPrice, pr.priceList.id) from DcsPrice pr where pr.dcsProduct in :products")
	List<PricedProductDTO> getProductsPrices(@Param("products") List<DcsProduct> products);
}
