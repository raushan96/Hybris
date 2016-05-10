package de.andre.service.price;

import de.andre.entity.catalog.Price;
import de.andre.entity.catalog.PriceList;
import de.andre.entity.catalog.Product;
import de.andre.multisite.SiteManager;
import de.andre.repository.catalog.PriceListRepository;
import de.andre.repository.catalog.PriceRepository;
import de.andre.repository.catalog.PricesRepository;
import de.andre.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.andre.service.catalog.CatalogConstants.EMPTY_PRICE;

public class PriceTools {
    private static final Logger logger = LoggerFactory.getLogger(PriceTools.class);

    private final PriceRepository priceRepository;
    private final PriceListRepository priceListRepository;

    private PriceTools self;

    private String defaultPriceList;
    private boolean bulkPricing;

    public PriceTools(final PricesRepository pricesRepository) {
        this.priceRepository = pricesRepository.getPriceRepository();
        this.priceListRepository = pricesRepository.getPriceListRepository();
    }

    @Transactional(readOnly = true)
    public ProductPrice priceProduct(final String productId) {
        return self.priceProduct(productId, null);
    }

    @Transactional(readOnly = true)
    public Map<String, ProductPrice> priceProducts(final Collection<Product> products) {
        if (isBulkPricing()) {
            priceProductsBulk(products, null);
        }

        return self.priceProducts(products, null);
    }

    @Transactional(readOnly = true)
    public Map<String, ProductPrice> priceProductsBulk(final Collection<Product> products, final String priceListId) {
        final String resolvedPriceList = resolvePriceList(priceListId);
        if (!validPricingParams(products, resolvedPriceList)) {
            return Collections.emptyMap();
        }

        final Set<String> productIds = StreamUtils.productsToIds(products);
        final Map<String, ProductPrice> productPrices = productIds
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        prdId -> new ProductPrice(prdId, null, null))
                );

        PriceList currentPriceList = priceListRepository.findOne(resolvedPriceList);
        while (currentPriceList != null) {
            final List<Price> prices = priceRepository.pricesForProducts(
                    StreamUtils.productsToIds(products), currentPriceList.getId());
            priceProductsForLevel(productPrices, prices);
            currentPriceList = currentPriceList.getBasePriceList();
        }

        return productPrices;
    }

    @Transactional(readOnly = true)
    public Map<String, ProductPrice> priceProducts(final Collection<Product> products, final String priceListId) {
        final String resolvedPriceList = resolvePriceList(priceListId);
        if (!validPricingParams(products, resolvedPriceList)) {
            return Collections.emptyMap();
        }

        return products
                .stream()
                .map(Product::getId)
                .collect(Collectors.toMap(
                        prdId -> prdId,
                        prdId -> priceProductImpl(prdId, resolvedPriceList))
                );
    }

    @Transactional(readOnly = true)
    public ProductPrice priceProduct(final String productId, final String priceListId) {
        final String resolvedPriceList = resolvePriceList(priceListId);
        if (!validPricingParams(productId, resolvedPriceList)) {
            return EMPTY_PRICE;
        }

        logger.debug("Searching price for {} product and {} base price list", productId, resolvedPriceList);
        return priceProductImpl(productId, resolvedPriceList);
    }

    private ProductPrice priceProductImpl(final String productId, final String baseListId) {
        Assert.hasLength(productId, "Product id can't be empty");
        Assert.hasLength(baseListId, "Price list id can't be empty");

        PriceList currentPriceList = priceListRepository.findOne(baseListId);
        Price basePrice = null;

        //try to find base price
        while (currentPriceList != null &&
                (basePrice = priceRepository.listPrice(productId, currentPriceList.getId())) == null) {
            logger.debug("Not found price for {} price list", currentPriceList.getId());
            currentPriceList = currentPriceList.getBasePriceList();
        }

        if (basePrice == null) {
            logger.debug("Not found any price for {} product", productId);
            return EMPTY_PRICE;
        }

        final ProductPrice productPrice = new ProductPrice(productId, basePrice.getListPrice(), BigDecimal.ZERO);
        Price parentPrice = null;
        //continue with sales one
        currentPriceList = currentPriceList.getBasePriceList();
        while (currentPriceList != null &&
                (parentPrice = priceRepository.listPrice(productId, currentPriceList.getId())) == null) {
            logger.debug("Not found price for {} price list", currentPriceList.getId());
            currentPriceList = currentPriceList.getBasePriceList();
        }
        if (parentPrice != null) {
            productPrice.setBasePrice(parentPrice.getListPrice());
            productPrice.setSalePrice(basePrice.getListPrice());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found {} price, for {} priceList",
                         productPrice,
                         ObjectUtils.nullSafeToString(currentPriceList));
        }
        return productPrice;
    }

    private void priceProductsForLevel(final Map<String, ProductPrice> productPrices, final List<Price> prices) {
        for (final Price aPrice : prices) {
            final ProductPrice productPrice = productPrices.get(aPrice.getProduct().getId());
            if (productPrice != null) {
                productPrice.addPrice(aPrice.getListPrice());
            }
        }
    }

    private String resolvePriceList(final String pListId) {
        if (StringUtils.hasLength(pListId)) {
            return pListId;
        }

        final String sitePriceList = SiteManager.getSite().priceListId();
        if (StringUtils.hasLength(sitePriceList)) {
            return sitePriceList;
        }

        return getDefaultPriceList();
    }

    private boolean validPricingParams(final String productId, final String baseListId) {
        return StringUtils.hasLength(productId) && StringUtils.hasLength(baseListId);
    }

    private boolean validPricingParams(final Collection<Product> products, final String baseListId) {
        return !CollectionUtils.isEmpty(products) && StringUtils.hasLength(baseListId);
    }

    public String getDefaultPriceList() {
        return defaultPriceList;
    }

    public void setDefaultPriceList(String defaultPriceList) {
        this.defaultPriceList = defaultPriceList;
    }

    public boolean isBulkPricing() {
        return bulkPricing;
    }

    public void setBulkPricing(boolean bulkPricing) {
        this.bulkPricing = bulkPricing;
    }

    public void setSelf(PriceTools self) {
        this.self = self;
    }
}
