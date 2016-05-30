package de.andre.entity.order;

import de.andre.entity.catalog.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hcm_item", schema = "hybris")
public class CommerceItem extends CommerceIdentifier {
    private Long version;
    private Long quantity;
    private LocalDateTime creationDate;

    private Order order;
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Version
    @Column(name = "version")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
}
