package de.andre.entity.profile;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hp_wish_item", schema = "hybris")
public class WishItem extends ProfileBaseEntity {
    private String displayName;
    private String description;
    private Integer quantityDesired;

    private WishList wishList;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    @NotEmpty
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Column(name = "quantity_desired")
    public Integer getQuantityDesired() {
        return quantityDesired;
    }

    public void setQuantityDesired(Integer quantityDesired) {
        this.quantityDesired = quantityDesired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishItem wishItem = (WishItem) o;

        if (!displayName.equals(wishItem.displayName)) return false;
        if (description != null ? !description.equals(wishItem.description) : wishItem.description != null)
            return false;
        return quantityDesired.equals(wishItem.quantityDesired);

    }

    @Override
    public int hashCode() {
        int result = displayName.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + quantityDesired.hashCode();
        return result;
    }
}
