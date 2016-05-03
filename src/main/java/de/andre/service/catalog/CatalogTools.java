package de.andre.service.catalog;

import de.andre.entity.catalog.Category;
import de.andre.entity.catalog.Product;
import de.andre.multisite.SiteManager;
import de.andre.repository.catalog.PricesRepository;
import de.andre.repository.catalog.ProductCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
        final List<Product> allProducts = getProductsWithAncestorsCat(cat);

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

    private List<Product> getProductsWithAncestorsCat(final Category rootCategory) {
        final List<Product> resultList = new ArrayList<>(30);
        final Set<String> seenSoFar = new HashSet<>();

        processCategory(rootCategory, seenSoFar, resultList);

        if (!resultList.isEmpty()) {
            Collections.sort(resultList);
        }
        return resultList;
    }

    private void processCategory(final Category category, final Set<String> seenSoFar,
            final List<Product> resultList) {
        if (seenSoFar.contains(category.getId())) {
            return;
        }
        seenSoFar.add(category.getId());

        if (!CollectionUtils.isEmpty(category.getProducts())) {
            resultList.addAll(category.getProducts());
        }
        if (!CollectionUtils.isEmpty(category.getChildCategories())) {
            for (final Category subcategory : category.getChildCategories()) {
                processCategory(subcategory, seenSoFar, resultList);
            }
        }
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

        final Category rootCategory = productCatalog.getCategoryRepository()
                .rootCatalogCategory(catalogId);
        if (rootCategory == null) {
            logger.warn("No root category configured for {} catalog", catalogId);
            return Collections.emptySet();
        }

        return rootCategory.getChildCategories();
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
