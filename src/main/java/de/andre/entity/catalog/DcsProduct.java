package de.andre.entity.catalog;

import de.andre.entity.enums.ProductType;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by andreika on 4/21/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_PRODUCT", schema = "HYBRIS")
public class DcsProduct implements Comparable<DcsProduct> {
    private Integer productId;
    private String displayName;
    private String description;
    private String image;
    private ProductType productType;
    private Boolean discountable;
    private Date startDate;
    private Date expirationDate;
    private String brand;
    private Double basePrice;
    private Double salePrice;
    private List<DcsCategory> parentCategories;

    @Id
    @Column(name = "PRODUCT_ID")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @ManyToMany(mappedBy = "childProducts")
    public List<DcsCategory> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(List<DcsCategory> parentCategories) {
        this.parentCategories = parentCategories;
    }

    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IMAGE")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Column(name = "DISCOUNTABLE")
    @Type(type = "boolean")
    public Boolean getDiscountable() {
        return discountable;
    }

    public void setDiscountable(Boolean discountable) {
        this.discountable = discountable;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DATE")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Transient
    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    @Transient
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DcsProduct that = (DcsProduct) o;

        if (!Objects.equals(productId, that.productId)) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (productType != null ? !productType.equals(that.productType) : that.productType != null) return false;
        if (discountable != null ? !discountable.equals(that.discountable) : that.discountable != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, displayName, description, image, productType, discountable, startDate, expirationDate, brand);
    }

    private static class ProductTypeOrder implements Comparator<DcsProduct>, Serializable {
        private static final long serialVersionUID = -8013165364488905721L;

        @Override
        public int compare(DcsProduct o1, DcsProduct o2) {
            return o1.productType.ordinal() - o2.productType.ordinal();
        }
    }

    private static Comparator<DcsProduct> PRODUCT_TYPE_COMPARATOR = new ProductTypeOrder();

    @Override
    public int compareTo(DcsProduct o) {
        int brandDiff = brand.compareTo(o.brand);
        if (brandDiff != 0)
            return brandDiff;
        return displayName.compareTo(o.displayName);
    }
}
