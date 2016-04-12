package de.andre.entity.dto;

import java.io.Serializable;

public class PricedProductDTO implements Serializable {
    private static final long serialVersionUID = 2589974500915152258L;

    private Integer productId;
    private Double price;
    private String priceList;

    public PricedProductDTO(){};

    public PricedProductDTO(Integer productId, Double basePrice, String priceList) {
        this.productId = productId;
        this.price = basePrice;
        this.priceList = priceList;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }
}
