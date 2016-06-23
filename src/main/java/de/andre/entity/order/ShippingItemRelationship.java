package de.andre.entity.order;

import de.andre.entity.enums.RelationshipType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hcm_ship_item", schema = "hybris")
public class ShippingItemRelationship extends CommerceIdentifier {
    private Long quantity;
    private RelationshipType type;

    private HardgoodShippingGroup shippingGroup;
    private CommerceItem commerceItem;

    @NotNull
    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ship_group_id")
    public HardgoodShippingGroup getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(HardgoodShippingGroup shippingGroup) {
        this.shippingGroup = shippingGroup;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    public CommerceItem getCommerceItem() {
        return commerceItem;
    }

    public void setCommerceItem(CommerceItem commerceItem) {
        this.commerceItem = commerceItem;
    }

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public RelationshipType getType() {
        return type;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingItemRelationship that = (ShippingItemRelationship) o;

        if (!quantity.equals(that.quantity)) return false;
        if (!getId().equals(that.getId())) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = quantity.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }
}
