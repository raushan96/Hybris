package de.andre.entity.profile;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "hp_wish_list", schema = "hybris")
public class WishList {
    private Long id;
    private boolean published;
    private LocalDateTime creationDate;

    private Profile profile;
    private Address shippingAddress;
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<WishItem> wishItems = new ArrayList<>(0);

    @Id
    @GeneratedValue(generator="gen")
    @GenericGenerator(
            name="gen",
            strategy="foreign",
            parameters=@org.hibernate.annotations.Parameter(name="property", value="profile")
    )
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipping_addr_id")
    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index_col")
    public List<WishItem> getWishItems() {
        return wishItems;
    }

    public void setWishItems(List<WishItem> wishItems) {
        this.wishItems = wishItems;
    }

    @Type(type = "boolean")
    @Column(name = "is_published")
    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Generated(GenerationTime.INSERT)
    @Column(name = "creation_date")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishList wishList = (WishList) o;

        if (published != wishList.published) return false;
        return !(creationDate != null ? !creationDate.equals(wishList.creationDate) : wishList.creationDate != null);

    }

    @Override
    public int hashCode() {
        int result = (published ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
