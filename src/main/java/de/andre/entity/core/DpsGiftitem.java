package de.andre.entity.core;

import javax.persistence.*;

/**
 * Created by Andrei on 3/30/2015.
 */
@Entity
@Table(name = "DPS_GIFTITEM", schema = "HYBRIS")
public class DpsGiftitem {
	private Integer giftItemId;
	private String displayName;
	private String description;
	private Long quantityDesired;
	private Long quantityPurchased;
	private DpsGiftlist dpsGiftlist;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
	@SequenceGenerator(name = "profileSeq", sequenceName = "profile_seq")
	@Column(name = "GIFT_ITEM_ID")
	public Integer getGiftItemId() {
		return giftItemId;
	}

	public void setGiftItemId(Integer giftItemId) {
		this.giftItemId = giftItemId;
	}

	@ManyToOne
	@JoinColumn(name = "GIFT_LIST_ID")
	public DpsGiftlist getDpsGiftlist() {
		return dpsGiftlist;
	}

	public void setDpsGiftlist(DpsGiftlist dpsGiftlist) {
		this.dpsGiftlist = dpsGiftlist;
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

	@Column(name = "QUANTITY_DESIRED")
	public Long getQuantityDesired() {
		return quantityDesired;
	}

	public void setQuantityDesired(Long quantityDesired) {
		this.quantityDesired = quantityDesired;
	}

	@Column(name = "QUANTITY_PURCHASED")
	public Long getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Long quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DpsGiftitem that = (DpsGiftitem) o;

		if (giftItemId != null ? !giftItemId.equals(that.giftItemId) : that.giftItemId != null) return false;
		if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (quantityDesired != null ? !quantityDesired.equals(that.quantityDesired) : that.quantityDesired != null)
			return false;
		if (quantityPurchased != null ? !quantityPurchased.equals(that.quantityPurchased) : that.quantityPurchased != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = giftItemId != null ? giftItemId.hashCode() : 0;
		result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (quantityDesired != null ? quantityDesired.hashCode() : 0);
		result = 31 * result + (quantityPurchased != null ? quantityPurchased.hashCode() : 0);
		return result;
	}
}
