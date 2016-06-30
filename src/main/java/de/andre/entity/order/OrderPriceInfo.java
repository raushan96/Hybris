package de.andre.entity.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("order")
public class OrderPriceInfo extends PriceInfo {
    private BigDecimal shipping;
    private BigDecimal tax;

    private Order order;

    @OneToOne(optional = false, mappedBy = "priceInfo")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "shipping")
    public BigDecimal getShipping() {
        return shipping;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public void increaseShipping(BigDecimal shipping) {
        setShipping(getShipping().add(shipping));
    }

    @Column(name = "tax")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderPriceInfo that = (OrderPriceInfo) o;

        if (shipping != null ? !shipping.equals(that.shipping) : that.shipping != null) return false;
        return tax != null ? tax.equals(that.tax) : that.tax == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (shipping != null ? shipping.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        return result;
    }
}
