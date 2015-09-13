package de.andre.entity.order;

import de.andre.entity.core.DpsUser;
import de.andre.entity.enums.OrderState;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DCSPP_ORDER", schema = "HYBRIS")
public class DcsppOrder {
	private Integer orderId;
	private String orderNumber;
	private String couponId;
	private Long version;
	private OrderState state;
	private Date creationDate;
	private Date submittedDate;
	private Date lastModifiedDate;
	private DpsUser dpsUser;
	private Set<DcsppShipGroup> shippingGroups;
	private Set<DcsppPayGroup> paymentGroups;
	private Set<DcsppItem> commerceItems;
	private DcsppAmountInfo amountInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
	@Column(name = "ORDER_ID")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public DpsUser getDpsUser() {
		return dpsUser;
	}

	public void setDpsUser(DpsUser dpsUser) {
		this.dpsUser = dpsUser;
	}

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//	@Fetch(value = FetchMode.SUBSELECT)
	public Set<DcsppShipGroup> getShippingGroups() {
		return shippingGroups;
	}

	public void setShippingGroups(Set<DcsppShipGroup> shippingGroups) {
		this.shippingGroups = shippingGroups;
	}

	public void addShippingGroup(DcsppShipGroup shipGroup) {
		if (getShippingGroups() == null) {
			shippingGroups = new HashSet<>();
		}
		this.getShippingGroups().add(shipGroup);
		shipGroup.setOrder(this);
	}

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<DcsppPayGroup> getPaymentGroups() {
		return paymentGroups;
	}

	public void setPaymentGroups(Set<DcsppPayGroup> paymentGroups) {
		this.paymentGroups = paymentGroups;
	}

	public void addPaymentGroup(DcsppPayGroup payGroup) {
		if (getPaymentGroups() == null) {
			paymentGroups = new HashSet<>();
		}
		this.getPaymentGroups().add(payGroup);
		payGroup.setOrder(this);
	}

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<DcsppItem> getCommerceItems() {
		return commerceItems;
	}

	public void setCommerceItems(Set<DcsppItem> commerceItems) {
		this.commerceItems = commerceItems;
	}

	public void addCommerceItem(DcsppItem commerceItem) {
		if (getCommerceItems() == null) {
			commerceItems = new HashSet<>();
		}
		this.getCommerceItems().add(commerceItem);
		commerceItem.setOrder(this);
	}

	public void removeCommerceItem(DcsppItem commerceItem) {
		if (getCommerceItems() == null) {
			return;
		}
		getCommerceItems().remove(commerceItem);
		commerceItem.setOrder(null);
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_PRICE_INFO", insertable = true, updatable = true)
	public DcsppAmountInfo getAmountInfo() {
		return amountInfo;
	}

	public void setAmountInfo(DcsppAmountInfo amountInfo) {
		this.amountInfo = amountInfo;
	}

	@Column(name = "ORDER_NUMBER")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "COUPON_ID")
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "STATE")
	@Enumerated(EnumType.ORDINAL)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "SUBMITTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsppOrder that = (DcsppOrder) o;

		if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
		if (couponId != null ? !couponId.equals(that.couponId) : that.couponId != null) return false;
		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (state != null ? !state.equals(that.state) : that.state != null) return false;
		if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
		if (submittedDate != null ? !submittedDate.equals(that.submittedDate) : that.submittedDate != null)
			return false;
		return !(lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null);

	}

	@Override
	public int hashCode() {
		int result = (orderNumber != null ? orderNumber.hashCode() : 0);
		result = 31 * result + (couponId != null ? couponId.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
		result = 31 * result + (submittedDate != null ? submittedDate.hashCode() : 0);
		result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
		return result;
	}
}
