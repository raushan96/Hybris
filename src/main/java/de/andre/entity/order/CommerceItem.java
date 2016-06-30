package de.andre.entity.order;

import de.andre.entity.catalog.Product;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hcm_item", schema = "hybris")
public class CommerceItem extends CommerceIdentifier {
    private Long quantity;
    private LocalDateTime creationDate;

    private Order order;
    private Product product;
    private ItemPriceInfo priceInfo;
    private Set<ShippingItemRelationship> shippingItemRelationships;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToMany(mappedBy = "commerceItem")
    public Set<ShippingItemRelationship> getShippingItemRelationships() {
        return shippingItemRelationships;
    }

    public void setShippingItemRelationships(Set<ShippingItemRelationship> shippingItemRelationship) {
        this.shippingItemRelationships = shippingItemRelationship;
    }

    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_info_id")
    public ItemPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(ItemPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public Set<ShippingItemRelationship> shipRelsInternal() {
        if (this.shippingItemRelationships == null) {
            this.shippingItemRelationships = new HashSet<>();
        }
        return this.shippingItemRelationships;
    }

    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "creation_date")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean hasShippingRels() {
        return !CollectionUtils.isEmpty(getShippingItemRelationships());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommerceItem that = (CommerceItem) o;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (product.getId() != null ? !product.getId().equals(that.product.getId()) : that.product.getId() != null) return false;
        return creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = quantity != null ? quantity.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (product != null ? product.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommerceItem{" +
                "quantity=" + quantity +
                ", creationDate=" + creationDate +
                '}';
    }
}
