package de.andre.entity.order;

import de.andre.entity.enums.ShippingState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class ShippingGroup extends CommerceIdentifier {
    private ShippingState shippingState;

    private Order order;

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
}
