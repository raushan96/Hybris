package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by andreika on 4/25/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_PRICE", schema = "HYBRIS")
public class DcsPrice {
    private Integer priceId;
    private Double listPrice;
    private DcsPriceList priceList;
    private DcsProduct dcsProduct;

    @Id
    @Column(name = "PRICE_ID")
    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    @OneToOne
    @JoinColumn(name = "PRICE_LIST_ID")
    public DcsPriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(DcsPriceList priceList) {
        this.priceList = priceList;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    public DcsProduct getDcsProduct() {
        return dcsProduct;
    }

    public void setDcsProduct(DcsProduct dcsProduct) {
        this.dcsProduct = dcsProduct;
    }

    @Basic
    @Column(name = "LIST_PRICE")
    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DcsPrice dcsPrice = (DcsPrice) o;

        if (priceId != dcsPrice.priceId) return false;
        if (listPrice != null ? !listPrice.equals(dcsPrice.listPrice) : dcsPrice.listPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceId, listPrice);
    }
}
