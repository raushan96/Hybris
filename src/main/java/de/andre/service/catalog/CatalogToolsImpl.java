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

import java.util.ArrayList;
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
		final Map<String, String> categoryNameIdMap = new LinkedHashMap<>();
		final List<String> subCategoryNames = getSubcategoriesNames(catId, categoryNameIdMap);
		if (subCategoryNames == null) {
			logger.warn("No subcategories found.");
			return null;
		}

		List<DcsProduct> allProducts = getProductsByCatId(catId);
		if (allProducts == null || allProducts.size() == 0) {
			logger.warn("No products found.");
			return null;
		}

		return null;
	}

	private List<DcsProduct> getProductsByCatId(final String catId) {
		try {
			return null;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	private List<String> getSubcategoriesNames(final String catId, final Map<String, String> categoryNameIdMap) {
		if (StringUtils.hasText(catId)){
			try {
				final List<String[]> childCategories = catalogRepository.getSubCategoriesIdsAndNames(catId);

				if(childCategories == null || childCategories.isEmpty()) {
					logger.warn("No child categories found for category: ", catId);
					return null;
				}

				final List<String> subCategoriesNames = new ArrayList<>(childCategories.size());
				for (String[] dcsCategory : childCategories) {
					subCategoriesNames.add(dcsCategory[1]);
					categoryNameIdMap.put(dcsCategory[1], dcsCategory[0]);
				}
				return subCategoriesNames;
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
