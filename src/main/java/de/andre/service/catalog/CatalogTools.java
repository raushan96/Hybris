package de.andre.service.catalog;

import de.andre.entity.catalog.Category;
import de.andre.entity.catalog.Product;
import de.andre.multisite.SiteManager;
import de.andre.repository.catalog.PricesRepository;
import de.andre.repository.catalog.ProductCatalog;
import de.andre.service.price.PriceTools;
import de.andre.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogTools {
    private static final Logger logger = LoggerFactory.getLogger(CatalogTools.class);

    private String defaultCatalogId;

    private final ProductCatalog productCatalog;
    private final PriceTools priceTools;
    private final PricesRepository pricesRepository;

    public CatalogTools(final ProductCatalog productCatalog, final PriceTools priceTools,
            final PricesRepository pricesRepository) {
        this.productCatalog = productCatalog;
        this.priceTools = priceTools;
        this.pricesRepository = pricesRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> assembleCategoryContent(final String catId) {
        final Category cat;
        if (!StringUtils.hasLength(catId) || (cat = productCatalog.getCategoryRepository().findOne(catId)) == null) {
            logger.debug("Empty or invalid category id {}", catId);
            return Collections.emptyMap();
        }

        final Category parentCat = cat.getParentCategory();
        final String filterCatId = getFilterCategoryId(cat, parentCat);
        final List<Product> allProducts = getProductsWithAncestorsCat(cat);

        final Map<String, String> categoryIdNameMap = getSubcategoriesNames(cat, parentCat, filterCatId);
        if (categoryIdNameMap.isEmpty()) {
            logger.warn("No subcategories found for category {}", catId);
        }

        final Map<String, Object> result = new HashMap<>(5);
        result.put("products", allProducts);
        result.put("prices", priceTools.priceProducts(allProducts));
        result.put("catsNameId", categoryIdNameMap);
        result.put("parentCat", parentCat);

        return result;
    }

    private List<Product> getProductsWithAncestorsCat(final Category rootCategory) {
        // let's try to find and filter products in memory
        final List<Product> resultList = new ArrayList<>(30);
        final Set<String> seenSoFar = new HashSet<>();

        processCategory(rootCategory, seenSoFar, resultList);

        final LocalDateTime now = LocalDateTime.now();
        final String siteId = SiteManager.getSiteId();
        logger.trace("Filtering {} products by {} date and {} site",
                     resultList.size(), now.toString(), siteId);

        return resultList
                .stream()
//                .filter(prd -> prd contains(siteId))
                .filter(prd -> prd.isEnabled() && (prd.getExpirationDate() == null || prd.getExpirationDate().isAfter(
                        now)) &&
                        prd.getStartDate() == null || prd.getStartDate().isBefore(now))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
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

    private Map<String, String> getSubcategoriesNames(final Category baseCategory, final Category parentCategory,
            final String filterCatId) {
        final Set<Category> subcategories;
        if (baseCategory.getId().equals(filterCatId)) {
            logger.debug("Using base category: {}", baseCategory.getId());
            subcategories = baseCategory.getChildCategories();
        } else {
            logger.debug("Using parent category: {}", parentCategory.getId());
            subcategories = parentCategory.getChildCategories();
        }

        if (subcategories.isEmpty()) {
            logger.debug("No child categories found for category: {}, fallback to root category", filterCatId);
            return StreamUtils.categoriesToSortedIdNames(getRootChildCategories());
        }

        return StreamUtils.categoriesToSortedIdNames(subcategories);
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
