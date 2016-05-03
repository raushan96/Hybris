package de.andre.service.catalog;

import de.andre.entity.catalog.Category;
import de.andre.entity.catalog.Product;
import de.andre.multisite.SiteManager;
import de.andre.repository.catalog.PricesRepository;
import de.andre.repository.catalog.ProductCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

public class CatalogTools {
    private static final Logger logger = LoggerFactory.getLogger(CatalogTools.class);

    private String defaultCatalogId;

    private final ProductCatalog productCatalog;
    private final PricesRepository pricesRepository;

    public CatalogTools(final ProductCatalog productCatalog, final PricesRepository pricesRepository) {
        this.productCatalog = productCatalog;
        this.pricesRepository = pricesRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> populateCategoryMap(final String catId) {
        final Category cat;
        if (!StringUtils.hasLength(catId) || (cat = productCatalog.getCategoryRepository().findOne(catId)) == null) {
            logger.debug("Empty or invalid category id {}", catId);
            return Collections.emptyMap();
        }

        final Category parentCat = cat.getParentCategory();
        final String filterCatId = getFilterCategoryId(cat, parentCat);

        final List<Product> allProducts = getProductsByCatId(catId);
        if (allProducts.isEmpty()) {
            logger.warn("Category {} has no products", catId);
        } else {
            Collections.sort(allProducts);
        }

        final Map<String, String> categoryIdNameMap = getSubcategoriesNames(filterCatId);
        if (categoryIdNameMap == null || categoryIdNameMap.isEmpty()) {
            logger.warn("No subcategories found for category {}, returning without processing", catId);
        }

        final Map<String, Object> result = new HashMap<>(5);
        result.put("products", allProducts);
        result.put("catsNameId", categoryIdNameMap);
        result.put("parentCat", parentCat);

        return result;
    }

    private String getFilterCategoryId(final Category category, final Category parentCategory) {
        if (!category.getChildCategories().isEmpty()) {
            return category.getId();
        } else {
            if (parentCategory != null) {
                return parentCategory.getId();
            }
        }

        logger.debug("No child or parent categories for {} category", category.getId());
        return category.getId();
    }

    private List<Product> getProductsByCatId(final String catId) {
        final List<Product> allProducts = productCatalog.getProductRepository()
                .getProductsWithAncestorsCat(catId);
        return allProducts;
    }

    private Map<String, String> getSubcategoriesNames(final String catId) {
        final List<String[]> childCategories = productCatalog.getCatalogRepository()
                .getSubCategoriesIdsAndNames(catId);

        if (childCategories.isEmpty()) {
            logger.debug("No child categories found for category: {}", catId);
            return Collections.emptyMap();
        }

        final Map<String, String> categoryIdNameMap = new LinkedHashMap<>(childCategories.size());
        for (final Object[] category : childCategories) {
            categoryIdNameMap.put(
                    String.valueOf(category[0]), String.valueOf(category[1]));
        }
        return categoryIdNameMap;
    }

    @Transactional(readOnly = true)
    public Set<Category> getRootChildCategories() {
        String catalogId = SiteManager.getSite().catalogId();
        if (!StringUtils.hasLength(catalogId)) {
            logger.warn("Site {} doesn't have catalog, using default one", SiteManager.getSiteId());
            catalogId = getDefaultCatalogId();
        }
        return productCatalog.getCatalogRepository()
                .rootCatalogCategories(catalogId);
    }

    @Transactional(readOnly = true)
    public Product getProductById(final String prdId) {
        return productCatalog.getProductRepository().findOne(prdId);
    }

    public String getDefaultCatalogId() {
        return defaultCatalogId;
    }

    public void setDefaultCatalogId(String defaultCatalogId) {
        this.defaultCatalogId = defaultCatalogId;
    }
}
