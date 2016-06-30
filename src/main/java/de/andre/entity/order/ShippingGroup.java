package de.andre.entity.order;

import de.andre.entity.enums.ShippingState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class ShippingGroup extends CommerceIdentifier {
    private ShippingState shippingState;

    private Order order;
    private ShippingPriceInfo priceInfo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ship_state")
    @NotNull
    public ShippingState getShippingState() {
        return shippingState;
    }

    public void setShippingState(ShippingState shippingState) {
        this.shippingState = shippingState;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_info_id")
    public ShippingPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(ShippingPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingGroup that = (ShippingGroup) o;

        return shippingState == that.shippingState;

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (shippingState != null ? shippingState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShippingGroup{" +
                "shippingState=" + shippingState +
                '}';
    }
}
