package de.andre.entity.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Entity
@Table(name = "hc_price_list", schema = "hybris")
public class PriceList {
    private String id;
    private String description;
    private LocalDateTime creationDate;
    private Locale locale;
    private Currency currency;

    private PriceList basePriceList;
    private List<Price> prices;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToOne
    @JoinColumn(name = "base_price_list")
    public PriceList getBasePriceList() {
        return basePriceList;
    }

    @OneToMany
    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public void setBasePriceList(PriceList basePriceList) {
        this.basePriceList = basePriceList;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(GenerationTime.INSERT)
    @Column(name = "creation_date", insertable = false, updatable = false)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Type(type = "locale")
    @Column(name = "locale")
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Type(type = "currency")
    @Column(name = "currency")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceList that = (PriceList) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (locale != null ? !locale.equals(that.locale) : that.locale != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id='" + id + '\'' +
                ", locale=" + locale +
                ", currency=" + currency +
                '}';
    }
}
