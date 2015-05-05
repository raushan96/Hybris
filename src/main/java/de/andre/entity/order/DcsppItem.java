package de.andre.entity.order;

import de.andre.entity.catalog.DcsProduct;

import javax.persistence.*;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_ITEM", schema = "HYBRIS")
public class DcsppItem {
	private Integer commerceItemId;
	private Long version;
	private Integer quantity;
	private DcsProduct product;
	private DcsppOrder order;
	private DcsppAmountInfo amountInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
	@Column(name = "COMMERCE_ITEM_ID")
	public Integer getCommerceItemId() {
		return commerceItemId;
	}

	public void setCommerceItemId(Integer commerceItemId) {
		this.commerceItemId = commerceItemId;
	}

	@OneToOne
	@JoinColumn(name = "PRODUCT_ID", insertable = true, updatable = true)
	public DcsProduct getProduct() {
		return product;
	}

	public void setProduct(DcsProduct product) {
		this.product = product;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRICE_INFO")
	public DcsppAmountInfo getAmountInfo() {
		return amountInfo;
	}

	public void setAmountInfo(DcsppAmountInfo amountInfo) {
		this.amountInfo = amountInfo;
	}

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	public DcsppOrder getOrder() {
		return order;
	}

	public void setOrder(DcsppOrder order) {
		this.order = order;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	
	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsppItem dcsppItem = (DcsppItem) o;

		if (version != null ? !version.equals(dcsppItem.version) : dcsppItem.version != null) return false;
		if (quantity != null ? !quantity.equals(dcsppItem.quantity) : dcsppItem.quantity != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = commerceItemId;
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		return result;
	}
}
