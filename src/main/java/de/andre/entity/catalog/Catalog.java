package de.andre.entity.catalog;

import de.andre.entity.site.SiteConfiguration;
import de.andre.entity.types.CommaDelimitedStringType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Entity
@Table(name = "hc_catalog", schema = "hybris")
public class Catalog {
    private String id;
    private String displayName;
    private String code;
    private LocalDateTime creationDate;

    private Set<SiteConfiguration> sites;

    private Set<Category> allChildCategories;
    private Set<Category> rootCategories;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToMany
    @JoinTable(
            name = "hc_catalog_categories",
            joinColumns = @JoinColumn(name = "catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OrderBy("displayName")
    public Set<Category> getAllChildCategories() {
        return allChildCategories;
    }

    public void setAllChildCategories(Set<Category> childCategories) {
        this.allChildCategories = childCategories;
    }

    public void setRootCategories(Set<Category> rootCategories) {
        this.rootCategories = rootCategories;
    }

    @OneToMany(mappedBy = "defaultCatalog")
    public Set<SiteConfiguration> getSites() {
        return sites;
    }

    public void setSites(Set<SiteConfiguration> sites) {
        this.sites = sites;
    }

    @NotBlank
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @NotBlank
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        Catalog hcCatalog = (Catalog) o;

        if (displayName != null ? !displayName.equals(hcCatalog.displayName) : hcCatalog.displayName != null)
            return false;
        if (code != null ? !code.equals(hcCatalog.code) : hcCatalog.code != null) return false;
        if (creationDate != null ? !creationDate.equals(hcCatalog.creationDate) : hcCatalog.creationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
