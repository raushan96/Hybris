package de.andre.entity.catalog;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by andreika on 4/21/2015.
 */
@Entity
@Immutable
@Table(name = "DCS_CATALOG", schema = "HYBRIS")
public class DcsCatalog {
	private String catalogId;
	private String displayName;
	private Timestamp creationDate;
	//private DcsCategory rootCategory;

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
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

/*	@OneToOne
	@JoinColumnsOrFormulas(
			{@JoinColumnOrFormula(formula = @JoinFormula(value = "(select category_id from dcs_category where root_category = 1)",
			referencedColumnName = "category_id")),
			@JoinColumnOrFormula(column = @JoinColumn())}
	)
	public DcsCategory getRootCategory() {
		return rootCategory;
	}*/

/*	public void setRootCategory(DcsCategory rootCategory) {
		this.rootCategory = rootCategory;
	}*/

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
