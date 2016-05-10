package de.andre.service.catalog;

import de.andre.service.price.ProductPrice;

import java.math.BigDecimal;

public class CatalogConstants {
    private CatalogConstants() {
    }

    public static final ProductPrice EMPTY_PRICE = new ProductPrice("", BigDecimal.ZERO, BigDecimal.ZERO);
}
