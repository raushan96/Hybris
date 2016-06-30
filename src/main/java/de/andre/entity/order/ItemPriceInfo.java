package de.andre.entity.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("item")
public class ItemPriceInfo extends PriceInfo {
    private Long quantityDiscounted;
    private CommerceItem commerceItem;

    @OneToOne(optional = false, mappedBy = "priceInfo")
    public CommerceItem getCommerceItem() {
        return commerceItem;
    }

    public void setCommerceItem(CommerceItem commerceItem) {
        this.commerceItem = commerceItem;
    }

    @Column(name = "quantity_discounted")
    public Long getQuantityDiscounted() {
        return quantityDiscounted;
    }

    public void setQuantityDiscounted(Long quantityDiscounted) {
        this.quantityDiscounted = quantityDiscounted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemPriceInfo that = (ItemPriceInfo) o;

        return quantityDiscounted != null ? quantityDiscounted.equals(that.quantityDiscounted) : that.quantityDiscounted == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (quantityDiscounted != null ? quantityDiscounted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemPriceInfo{" +
                "quantityDiscounted=" + quantityDiscounted +
                 super.toString();
    }
}
