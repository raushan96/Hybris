package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by andreika on 4/21/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_CATEGORY", schema = "HYBRIS")
public class DcsCategory {
	private String categoryId;
	private String displayName;
	private String longDescription;
	private Boolean rootCategory;
	private List<DcsProduct> childProducts;
	private DcsCategory parentCategory;
	private List<DcsCategory> childCategories;

	@Id
	@Column(name = "CATEGORY_ID")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "dcs_category_products",
			joinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")},
			inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")})
	public List<DcsProduct> getChildProducts() {
		return childProducts;
	}

	public void setChildProducts(List<DcsProduct> childProducts) {
		this.childProducts = childProducts;
	}

	@OneToMany(mappedBy = "parentCategory")
	public List<DcsCategory> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<DcsCategory> childCategories) {
		this.childCategories = childCategories;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_CAT_ID")
	public DcsCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(DcsCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Column(name = "DISPLAY_NAME")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "LONG_DESCRIPTION")
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@Column(name = "ROOT_CATEGORY")
	@Type(type = "boolean")
	public Boolean getRootCategory() {
		return rootCategory;
	}

	public void setRootCategory(Boolean rootCategory) {
		this.rootCategory = rootCategory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsCategory that = (DcsCategory) o;

		if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
		if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
		if (longDescription != null ? !longDescription.equals(that.longDescription) : that.longDescription != null)
			return false;
		if (rootCategory != null ? !rootCategory.equals(that.rootCategory) : that.rootCategory != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, displayName, longDescription, rootCategory);
	}
}
