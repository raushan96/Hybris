package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Immutable
@Entity
@Table(name = "hc_product", schema = "hybris")
public class Product implements Comparable<Product> {
    private String id;
    private String displayName;
    private String productCode;
    private String description;
    private String image;
    private boolean discountable;
    private boolean enabled;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;

    private List<Category> categories;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "products")
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NotBlank
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @NaturalId
    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Type(type = "boolean")
    @Column(name = "discountable")
    public boolean isDiscountable() {
        return discountable;
    }

    public void setDiscountable(boolean discountable) {
        this.discountable = discountable;
    }

    @Type(type = "boolean")
    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "start_date")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Column(name = "expiration_date")
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product hcProduct = (Product) o;

        if (displayName != null ? !displayName.equals(hcProduct.displayName) : hcProduct.displayName != null)
            return false;
        if (productCode != null ? !productCode.equals(hcProduct.productCode) : hcProduct.productCode != null)
            return false;
        if (image != null ? !image.equals(hcProduct.image) : hcProduct.image != null) return false;
        if (startDate != null ? !startDate.equals(hcProduct.startDate) : hcProduct.startDate != null) return false;
        if (expirationDate != null ? !expirationDate.equals(
                hcProduct.expirationDate) : hcProduct.expirationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (productCode != null ? productCode.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Product pr) {
        return getDisplayName().compareTo(pr.getDisplayName());
    }
}
