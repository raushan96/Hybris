package de.andre.service.catalog;

import de.andre.entity.catalog.Category;
import de.andre.entity.catalog.Product;
import de.andre.entity.dto.PricedProductDTO;
import de.andre.multisite.SiteManager;
import de.andre.repository.catalog.PricesRepository;
import de.andre.repository.catalog.ProductCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.*;

public class CatalogTools {
    private static final Logger logger = LoggerFactory.getLogger(CatalogTools.class);

    private String defaultCatalogId;

    private static final String BASE_PRICE_LIST = "base-list";
    private static final String SALE_PRICE_LIST = "sales-list";

    private final ProductCatalog productCatalog;
    private final PricesRepository pricesRepository;

    public CatalogTools(final ProductCatalog productCatalog, final PricesRepository pricesRepository) {
        this.productCatalog = productCatalog;
        this.pricesRepository = pricesRepository;
    }

    @Transactional(readOnly = true)
    public void populateCategoryMap(final String catId, final Model map) {
        final Category parentCategory = catalogRepository.getCategoryParent(catId);
        final String filterCatId = getFilterCategoryId(catId, parentCategory);

        if (filterCatId == null || parentCategory == null || ROOT_CAT_ID.equals(filterCatId)) {
            map.addAttribute("error", true);
            return;
        }
        final List<Product> allProducts = getProductsByCatId(catId);
        if (allProducts == null || allProducts.size() == 0) {
            logger.warn("Category {} has no products", catId);
        } else {
            Collections.sort(allProducts);
        }

        final Map<String, String> categoryIdNameMap = getSubcategoriesNames(filterCatId);
        ;
        if (categoryIdNameMap == null || categoryIdNameMap.isEmpty()) {
            logger.warn("No subcategories found for category {}, returning without processing", catId);
        }
        map.addAttribute("products", allProducts);
        map.addAttribute("catsNameId", categoryIdNameMap);
        map.addAttribute("parentCat", parentCategory);
        map.addAttribute("error", false);
    }

    private String getFilterCategoryId(final String catId, final Category parentCategory) {
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
                        logger.warn("Price list wasn't found for: " + productPrice.getProductId());
                    }
                }
            }
            return allProducts;
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return Collections.emptyList();
    }

    private Map<String, String> getSubcategoriesNames(final String catId) {
        if (StringUtils.hasText(catId)) {
            try {
                final List<String[]> childCategories = catalogRepository.getSubCategoriesIdsAndNames(catId);
                final Map<String, String> categoryIdNameMap = new LinkedHashMap<>();

                if (childCategories.isEmpty()) {
                    logger.warn("No child categories found for category: ", catId);
                    return null;
                }

                for (final Object[] category : childCategories) {
                    categoryIdNameMap.put((String) category[0], (String) category[1]);
                }
                return categoryIdNameMap;
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }

        return Collections.emptyMap();
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
