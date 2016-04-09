package de.andre.entity.catalog;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "DCS_CATALOG", schema = "HYBRIS")
public class DcsCatalog {
    private String catalogId;
    private String displayName;
    private Date creationDate;

    @Id
    @Column(name = "CATALOG_ID")
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DcsCatalog that = (DcsCatalog) o;

        if (catalogId != null ? !catalogId.equals(that.catalogId) : that.catalogId != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId, creationDate, displayName);
    }
}
