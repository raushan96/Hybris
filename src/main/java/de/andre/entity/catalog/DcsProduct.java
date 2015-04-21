package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by andreika on 4/21/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_PRODUCT", schema = "HYBRIS")
public class DcsProduct {
	private Integer productId;
	private String displayName;
	private String description;
	private String image;
	private Integer productType;
	private Integer discountable;
	private Timestamp startDate;
	private Timestamp expirationDate;
	private String brand;

	@Id
	@Column(name = "PRODUCT_ID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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
	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	@Column(name = "DISCOUNTABLE")
	public Integer getDiscountable() {
		return discountable;
	}

	public void setDiscountable(Integer discountable) {
		this.discountable = discountable;
	}

	@Basic
	@Column(name = "START_DATE")
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Basic
	@Column(name = "EXPIRATION_DATE")
	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsProduct that = (DcsProduct) o;

		if (productId != that.productId) return false;
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
}
