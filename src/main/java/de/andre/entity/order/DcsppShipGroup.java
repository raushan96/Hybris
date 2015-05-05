package de.andre.entity.order;

import de.andre.entity.enums.ShippingMethod;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.enums.ShippingType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_SHIP_GROUP", schema = "HYBRIS")
public class DcsppShipGroup {
	private Integer shippingGroupId;
	private Long version;
	private ShippingMethod shippingMethod;
	private ShippingType shippingType;
	private Date shipOnDate;
	private ShippingState state;
	private DcsppOrder order;
	private DcsppAmountInfo amountInfo;
	private DcsppShipInfo shipInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
	@Column(name = "SHIPPING_GROUP_ID")
	public Integer getShippingGroupId() {
		return shippingGroupId;
	}

	public void setShippingGroupId(Integer shippingGroupId) {
		this.shippingGroupId = shippingGroupId;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRICE_INFO")
	public DcsppAmountInfo getAmountInfo() {
		return amountInfo;
	}

	public void setAmountInfo(DcsppAmountInfo amountInfo) {
		this.amountInfo = amountInfo;
	}

	@OneToOne(mappedBy = "shipGroup")
	public DcsppShipInfo getShipInfo() {
		return shipInfo;
	}

	public void setShipInfo(DcsppShipInfo shipInfo) {
		this.shipInfo = shipInfo;
	}

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	public DcsppOrder getOrder() {
		return order;
	}

	public void setOrder(DcsppOrder order) {
		this.order = order;
	}

	@Column(name = "SHIPPING_METHOD")
	@Enumerated(EnumType.ORDINAL)
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	
	@Column(name = "SHIPPING_TYPE")
	@Enumerated(EnumType.ORDINAL)
	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	
	@Column(name = "SHIP_ON_DATE")
	@Temporal(TemporalType.DATE)
	public Date getShipOnDate() {
		return shipOnDate;
	}

	public void setShipOnDate(Date shipOnDate) {
		this.shipOnDate = shipOnDate;
	}

	@Column(name = "STATE")
	@Enumerated(EnumType.ORDINAL)
	public ShippingState getState() {
		return state;
	}

	public void setState(ShippingState state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsppShipGroup that = (DcsppShipGroup) o;

		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (shippingMethod != null ? !shippingMethod.equals(that.shippingMethod) : that.shippingMethod != null)
			return false;
		if (shippingType != null ? !shippingType.equals(that.shippingType) : that.shippingType != null) return false;
		if (shipOnDate != null ? !shipOnDate.equals(that.shipOnDate) : that.shipOnDate != null) return false;
		if (state != null ? !state.equals(that.state) : that.state != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(shippingMethod, shippingType, state, shipOnDate);
	}
}
