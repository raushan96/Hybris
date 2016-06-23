package de.andre.service.commerce.order;

import de.andre.entity.catalog.Product;
import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.Order;
import de.andre.repository.order.CommerceRepository;
import de.andre.service.catalog.CatalogTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CommerceItemsTools {
    private static final Logger logger = LoggerFactory.getLogger(CommerceItemsTools.class);

    private final CatalogTools catalogTools;
    private final CommerceRepository commerceRepository;

    public CommerceItemsTools(final CatalogTools catalogTools, final CommerceRepository commerceRepository) {
        this.catalogTools = catalogTools;
        this.commerceRepository = commerceRepository;
    }

    public CommerceItem createCommerceItem(final Long pQuantity, final Product pProduct) {
        Assert.notNull(pProduct);
        Assert.isTrue(pQuantity != null && pQuantity > 0, "Quantity must be greater than zero, got: " + pQuantity);

        final CommerceItem ci = new CommerceItem();
        ci.setCreationDate(LocalDateTime.now());
        ci.setQuantity(pQuantity);
        ci.setProduct(pProduct);

        if (logger.isDebugEnabled()) {
            logger.debug("Created commerce item: {}, with product {}", ci, pProduct.getId());
        }

        return ci;
    }

    public CommerceItem createCommerceItem(final Long pQuantity, final String pProductId) {
        return createCommerceItem(pQuantity, catalogTools.getProductById(pProductId));
    }

    public CommerceItem modifyItemQuantity(final Order pOrder, final String productId, final Long pQuantity) {
        Assert.notNull(pOrder);
        Assert.hasLength(productId);
        Assert.notNull(pQuantity);

        final CommerceItem ci = findExistingItem(pOrder, productId);
        if (ci != null) {
            final Long mQuantity = ci.getQuantity() + pQuantity;
            if (mQuantity > 0) {
                logger.debug("Setting quantity to {} for {} commerce item", mQuantity, ci.getId());
                ci.setQuantity(mQuantity);
            }
            else {
                logger.debug("Removing {} commerce item from {} order", ci.getId(), pOrder.getId());
                pOrder.getCommerceItems().remove(ci);
            }
        }
        else if (pQuantity > 0) {
            logger.debug("No items found for {} product id, creating new commerce item", productId);
            final CommerceItem newItem = createCommerceItem(pQuantity, productId);
            pOrder.addCommerceItem(newItem);
            return newItem;
        }

        return ci;
    }

    public CommerceItem removeItem(final Order pOrder, final String productId) {
        return modifyItemQuantity(pOrder, productId, 0L);
    }

    private CommerceItem findExistingItem(final Order pOrder, final CommerceItem pItem) {
        return findExistingItem(pOrder, pItem.getProduct().getId());
    }

    private CommerceItem findExistingItem(final Order pOrder, final String pProductId) {
        final List<CommerceItem> cis = pOrder.getCommerceItems();
        if (!CollectionUtils.isEmpty(cis)) {
            return cis.stream()
                    .filter(ci -> Objects.equals(ci.getProduct().getId(), pProductId))
                    .findAny()
                    .orElse(null);
        }

        return null;
    }
}
