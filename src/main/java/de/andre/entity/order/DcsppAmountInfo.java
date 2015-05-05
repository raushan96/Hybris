package de.andre.entity.order;

import de.andre.entity.enums.AmountType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_AMOUNT_INFO", schema = "HYBRIS")
public class DcsppAmountInfo {
	private Integer amountInfoId;
	private AmountType type;
	private Long version;
	private String currencyCode;
	private Double amount;
	private Double rawAmount;
	private Double tax;
	private Double shipping;
	private Integer quantity;
	private Boolean discounted;
	private Boolean amountIsFinal;
	private List<DcsppPriceAdjust> priceAdjusts;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
	@Column(name = "AMOUNT_INFO_ID")
	public Integer getAmountInfoId() {
		return amountInfoId;
	}

	public void setAmountInfoId(Integer amountInfoId) {
		this.amountInfoId = amountInfoId;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE")
	public AmountType getType() {
		return type;
	}

	public void setType(AmountType type) {
		this.type = type;
	}


	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@OneToMany(mappedBy = "amountInfo")
	@OrderColumn(name = "sequence")
	public List<DcsppPriceAdjust> getPriceAdjusts() {
		return priceAdjusts;
	}

	public void setPriceAdjusts(List<DcsppPriceAdjust> priceAdjusts) {
		this.priceAdjusts = priceAdjusts;
	}

	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	@Column(name = "RAW_AMOUNT")
	public Double getRawAmount() {
		return rawAmount;
	}

	public void setRawAmount(Double rawAmount) {
		this.rawAmount = rawAmount;
	}


	@Column(name = "TAX")
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}


	@Column(name = "SHIPPING")
	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}


	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	@Column(name = "DISCOUNTED")
	@Type(type = "boolean")
	public Boolean getDiscounted() {
		return discounted;
	}

	public void setDiscounted(Boolean discounted) {
		this.discounted = discounted;
	}


	@Column(name = "AMOUNT_IS_FINAL")
	@Type(type = "boolean")
	public Boolean getAmountIsFinal() {
		return amountIsFinal;
	}

	public void setAmountIsFinal(Boolean amountIsFinal) {
		this.amountIsFinal = amountIsFinal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsppAmountInfo that = (DcsppAmountInfo) o;

		if (type != null ? !type.equals(that.type) : that.type != null) return false;
		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
		if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
		if (rawAmount != null ? !rawAmount.equals(that.rawAmount) : that.rawAmount != null) return false;
		if (tax != null ? !tax.equals(that.tax) : that.tax != null) return false;
		if (shipping != null ? !shipping.equals(that.shipping) : that.shipping != null) return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
		if (discounted != null ? !discounted.equals(that.discounted) : that.discounted != null) return false;
		if (amountIsFinal != null ? !amountIsFinal.equals(that.amountIsFinal) : that.amountIsFinal != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (type != null ? type.hashCode() : 0);
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (rawAmount != null ? rawAmount.hashCode() : 0);
		result = 31 * result + (tax != null ? tax.hashCode() : 0);
		result = 31 * result + (shipping != null ? shipping.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (discounted != null ? discounted.hashCode() : 0);
		result = 31 * result + (amountIsFinal != null ? amountIsFinal.hashCode() : 0);
		return result;
	}
}
