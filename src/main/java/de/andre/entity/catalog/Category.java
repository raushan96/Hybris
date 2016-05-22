package de.andre.entity.catalog;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.comparator.NullSafeComparator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Entity
@Table(name = "hc_category", schema = "hybris")
public class Category {
    private String id;
    private String displayName;
    private String longDescription;
    private Set<String> sites;
    private boolean rootCategory;
    private LocalDateTime creationDate;

    private Set<Catalog> catalogs;
    private Category parentCategory;
    private Set<Category> childCategories;

    private List<Product> products;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "allChildCategories")
    public Set<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Set<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ManyToOne
    @JoinColumn(name = "parent_cat_id")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy = "parentCategory")
    @OrderBy("displayName")
    public Set<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<Category> childCategories) {
        this.childCategories = childCategories;
    }

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ManyToMany
    @JoinTable(
            name = "hc_category_products",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NotBlank
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "long_description")
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Type(type = "comma_delimited_set")
    @Column(name = "sites")
    public Set<String> getSites() {
        return sites;
    }

    public void setSites(Set<String> sites) {
        this.sites = sites;
    }

    @Type(type = "boolean")
    @Column(name = "root_category")
    public boolean isRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(boolean rootCategory) {
        this.rootCategory = rootCategory;
    }

    @Generated(GenerationTime.INSERT)
    @Column(name = "creation_date", insertable = false, updatable = false)
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

        Category that = (Category) o;

        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (longDescription != null ? !longDescription.equals(that.longDescription) : that.longDescription != null)
            return false;
        if (sites != null ? !sites.equals(that.sites) : that.sites != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (longDescription != null ? longDescription.hashCode() : 0);
        result = 31 * result + (sites != null ? sites.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    public static final Comparator<Category> NAME_COMPARATOR = new NullSafeComparator<>(
            new Comparator<Category>() {
                @Override
                public int compare(final Category cat1, final Category cat2) {
                    return cat1.getDisplayName().compareTo(cat2.getDisplayName());
                }
            }, true);
}
