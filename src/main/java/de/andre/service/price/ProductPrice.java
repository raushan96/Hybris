package de.andre.service.price;

import java.math.BigDecimal;

public class ProductPrice {
    private String productId;
    private BigDecimal basePrice;
    private BigDecimal salePrice;

    public ProductPrice() {
    }

    public ProductPrice(String productId, BigDecimal basePrice, BigDecimal salePrice) {
        this.productId = productId;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
    }

    // adds by descending price
    public boolean addPrice(final BigDecimal price) {
        if (price != null && BigDecimal.ZERO.compareTo(price) < 0) {
            if (this.basePrice == null) {
                this.basePrice = price;
                return true;
            } else if (this.salePrice == null) {
                this.salePrice = this.basePrice;
                this.basePrice = price;
                return true;
            }
        }

        return false;
    }

    public boolean isResolved() {
        return productId != null && basePrice != null;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "productId='" + productId + '\'' +
                ", basePrice=" + basePrice +
                ", salePrice=" + salePrice +
                '}';
    }
}
