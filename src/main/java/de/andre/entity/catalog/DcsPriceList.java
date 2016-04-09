package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by andreika on 4/25/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_PRICE_LIST", schema = "HYBRIS")
public class DcsPriceList {
    private String priceListId;
    private String description;
    private Timestamp creationDate;
    private String locale;
    private String currency;
    private DcsPriceList basePriceList;

    @Id
    @Column(name = "PRICE_LIST_ID")
    public String getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(String priceListId) {
        this.priceListId = priceListId;
    }

    @OneToOne
    @JoinColumn(name = "BASE_PRICE_LIST")
    public DcsPriceList getBasePriceList() {
        return basePriceList;
    }

    public void setBasePriceList(DcsPriceList basePriceList) {
        this.basePriceList = basePriceList;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CREATION_DATE")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "LOCALE")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DcsPriceList that = (DcsPriceList) o;

        if (priceListId != null ? !priceListId.equals(that.priceListId) : that.priceListId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (locale != null ? !locale.equals(that.locale) : that.locale != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = priceListId != null ? priceListId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
