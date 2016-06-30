package de.andre.entity.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("shipping")
public class ShippingPriceInfo extends PriceInfo {
    private HardgoodShippingGroup shippingGroup;

    @OneToOne(optional = false, mappedBy = "priceInfo")
    public HardgoodShippingGroup getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(HardgoodShippingGroup shippingGroup) {
        this.shippingGroup = shippingGroup;
    }

    @Override
    public String toString() {
        return "ShippingPriceInfo{" + super.toString();
    }
}
