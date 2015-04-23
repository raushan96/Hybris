package de.andre.service.catalog;

import de.andre.entity.catalog.DcsCategory;
import de.andre.entity.catalog.DcsProduct;
import de.andre.repository.CatalogRepository;
import de.andre.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreika on 4/20/2015.
 */

@Service
public class CatalogToolsImpl implements CatalogTools {
	private static final Logger logger = LoggerFactory.getLogger(CatalogTools.class);

	private final CatalogRepository catalogRepository;
	private final ProductRepository productRepository;

	@Autowired
	public CatalogToolsImpl(CatalogRepository catalogRepository, ProductRepository productRepository) {
		this.catalogRepository = catalogRepository;
		this.productRepository = productRepository;
	}

	public List<DcsProduct> getCatalogProducts(final String catId) {
		final String filterCatId = getFilterCategoryId(catId);

		List<DcsProduct> allProducts = getProductsByCatId(catId);
		if (allProducts == null || allProducts.size() == 0) {
			logger.warn("Category {0} has no products", catId);
			return null;
		}

		final Map<String, String> categoryNameIdMap = getSubcategoriesNames(filterCatId);;
		if (categoryNameIdMap == null || categoryNameIdMap.isEmpty()) {
			logger.warn("No subcategories found for category {0}, returning without processing", catId);
			return null;
		}
		return null;
	}

	private String getFilterCategoryId(final String catId) {
		if (StringUtils.hasText(catId)) {
			DcsCategory currentCat = catalogRepository.findCatWithChilds(catId);
			List<DcsCategory> subCategories = currentCat.getChildCategories();

			if (subCategories != null && !subCategories.isEmpty()) {
				return catId;
			} else {
				final String parentCatId = currentCat.getParentCategory().getCategoryId();
				if (parentCatId != null) {
					return parentCatId;
				}
			}
		}

		return null;
	}

	private List<DcsProduct> getProductsByCatId(final String catId) {
		try {
			return productRepository.getProductsWithAncestorsCat(catId);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	private Map<String, String> getSubcategoriesNames(final String catId) {
		if (StringUtils.hasText(catId)){
			try {
				final List<String[]> childCategories = catalogRepository.getSubCategoriesIdsAndNames(catId);
				final Map<String, String> categoryNameIdMap = new LinkedHashMap<>();

				if(childCategories.isEmpty()) {
					logger.warn("No child categories found for category: ", catId);
					return null;
				}

				for (Object[] dcsCategory : childCategories) {
					categoryNameIdMap.put((String) dcsCategory[1], (String) dcsCategory[0]);
				}
				return categoryNameIdMap;
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return null;
	}

	@Override
	public List<DcsCategory> getRootChildCategories() {
		return catalogRepository.getRootChildCategories();
	}
}
