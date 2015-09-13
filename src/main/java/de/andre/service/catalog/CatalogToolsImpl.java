package de.andre.service.catalog;

import de.andre.entity.catalog.DcsCategory;
import de.andre.entity.catalog.DcsProduct;
import de.andre.entity.dto.PricedProductDTO;
import de.andre.repository.CatalogRepository;
import de.andre.repository.PriceRepository;
import de.andre.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogToolsImpl implements CatalogTools {
	private static final Logger log = LoggerFactory.getLogger(CatalogTools.class);
	private static final String ROOT_CAT_ID = "root-cat";
	private static final String BASE_PRICE_LIST = "base-list";
	private static final String SALE_PRICE_LIST = "sales-list";

	private final CatalogRepository catalogRepository;
	private final ProductRepository productRepository;
	private final PriceRepository priceRepository;

	@Autowired
	public CatalogToolsImpl(final CatalogRepository catalogRepository, final ProductRepository productRepository,
							final PriceRepository priceRepository) {
		this.catalogRepository = catalogRepository;
		this.productRepository = productRepository;
		this.priceRepository = priceRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public void populateCategoryMap(final String catId, final ModelAndView mav) {
		final DcsCategory parentCategory = catalogRepository.getCategoryParent(catId);
		final String filterCatId = getFilterCategoryId(catId, parentCategory);

		if (filterCatId == null || parentCategory == null || ROOT_CAT_ID.equals(filterCatId)) {
			mav.addObject("error", true);
			return;
		}
		final List<DcsProduct> allProducts = getProductsByCatId(catId);
		if (allProducts == null || allProducts.size() == 0) {
			log.warn("Category {} has no products", catId);
		} else {
			Collections.sort(allProducts);
		}

		final Map<String, String> categoryIdNameMap = getSubcategoriesNames(filterCatId);;
		if (categoryIdNameMap == null || categoryIdNameMap.isEmpty()) {
			log.warn("No subcategories found for category {}, returning without processing", catId);
		}
		mav.addObject("products", allProducts);
		mav.addObject("catsNameId", categoryIdNameMap);
		mav.addObject("parentCat", parentCategory);
		mav.addObject("error", false);
	}

	private String getFilterCategoryId(final String catId, final DcsCategory parentCategory) {
		if (StringUtils.hasText(catId)) {
			Integer childCount = catalogRepository.getChildCatsCount(catId);

			if (childCount > 0) {
				return catId;
			} else {
				if (parentCategory != null) {
					return parentCategory.getCategoryId();
				}
			}
		}

		return null;
	}

	private List<DcsProduct> getProductsByCatId(final String catId) {
		try {
			final List<DcsProduct> allProducts = productRepository.getProductsWithAncestorsCat(catId);
			final List<PricedProductDTO> productPrices = priceRepository.getProductsPrices(allProducts);

			if (productPrices != null) {
				for (final PricedProductDTO productPrice : productPrices) {
					if (BASE_PRICE_LIST.equals(productPrice.getPriceList())) {
						for (final DcsProduct dcsProduct : allProducts) {
							if (dcsProduct.getProductId().equals(productPrice.getProductId())) {
								dcsProduct.setBasePrice(productPrice.getPrice());
								break;
							}
						}
					} else if (SALE_PRICE_LIST.equals(productPrice.getPriceList())) {
						for (final DcsProduct dcsProduct : allProducts) {
							if (dcsProduct.getProductId().equals(productPrice.getProductId())) {
								dcsProduct.setSalePrice(productPrice.getPrice());
								break;
							}
						}
					} else {
						log.warn("Price list wasn't found for: " + productPrice.getProductId());
					}
				}
			}
			return allProducts;
		} catch (Exception e) {
			log.error(e.toString());
		}

		return Collections.emptyList();
	}

	private Map<String, String> getSubcategoriesNames(final String catId) {
		if (StringUtils.hasText(catId)){
			try {
				final List<String[]> childCategories = catalogRepository.getSubCategoriesIdsAndNames(catId);
				final Map<String, String> categoryIdNameMap = new LinkedHashMap<>();

				if(childCategories.isEmpty()) {
					log.warn("No child categories found for category: ", catId);
					return null;
				}

				for (final Object[] dcsCategory : childCategories) {
					categoryIdNameMap.put((String) dcsCategory[0], (String) dcsCategory[1]);
				}
				return categoryIdNameMap;
			} catch (Exception e) {
				log.error(e.toString());
			}
		}

		return Collections.emptyMap();
	}

	@Transactional(readOnly = true)
	@Override
	public List<DcsCategory> getRootChildCategories() {
		return catalogRepository.getRootChildCategories();
	}

	@Transactional(readOnly = true)
	@Override
	public DcsProduct getProductById(Integer prdId) {
		return productRepository.findOne(prdId);
	}
}
