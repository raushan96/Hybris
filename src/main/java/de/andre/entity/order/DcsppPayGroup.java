package de.andre.entity.order;

import de.andre.entity.enums.PaymentMethod;
import de.andre.entity.enums.PaymentStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_PAY_GROUP", schema = "HYBRIS")
public class DcsppPayGroup {
	private Integer paymentGroupId;
	private Long version;
	private PaymentMethod paymentMethod;
	private Double amount;
	private Double amountAuthorized;
	private Double amountDebited;
	private Double amountCredited;
	private String currencyCode;
	private PaymentStatus state;
	private Date submittedDate;
	private DcsppOrder order;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
	@Column(name = "PAYMENT_GROUP_ID")
	public Integer getPaymentGroupId() {
		return paymentGroupId;
	}

	public void setPaymentGroupId(Integer paymentGroupId) {
		this.paymentGroupId = paymentGroupId;
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

	@Column(name = "PAYMENT_METHOD")
	@Enumerated(EnumType.ORDINAL)
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "AMOUNT_AUTHORIZED")
	public Double getAmountAuthorized() {
		return amountAuthorized;
	}

	public void setAmountAuthorized(Double amountAuthorized) {
		this.amountAuthorized = amountAuthorized;
	}

	@Column(name = "AMOUNT_DEBITED")
	public Double getAmountDebited() {
		return amountDebited;
	}

	public void setAmountDebited(Double amountDebited) {
		this.amountDebited = amountDebited;
	}

	@Column(name = "AMOUNT_CREDITED")
	public Double getAmountCredited() {
		return amountCredited;
	}

	public void setAmountCredited(Double amountCredited) {
		this.amountCredited = amountCredited;
	}

	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "STATE")
	@Enumerated(EnumType.ORDINAL)
	public PaymentStatus getState() {
		return state;
	}

	public void setState(PaymentStatus state) {
		this.state = state;
	}

	
	@Column(name = "SUBMITTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DcsppPayGroup that = (DcsppPayGroup) o;

		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (paymentMethod != null ? !paymentMethod.equals(that.paymentMethod) : that.paymentMethod != null)
			return false;
		if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
		if (amountAuthorized != null ? !amountAuthorized.equals(that.amountAuthorized) : that.amountAuthorized != null)
			return false;
		if (amountDebited != null ? !amountDebited.equals(that.amountDebited) : that.amountDebited != null)
			return false;
		if (amountCredited != null ? !amountCredited.equals(that.amountCredited) : that.amountCredited != null)
			return false;
		if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
		if (state != null ? !state.equals(that.state) : that.state != null) return false;
		if (submittedDate != null ? !submittedDate.equals(that.submittedDate) : that.submittedDate != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(paymentMethod, amount, amountAuthorized, amountCredited, amountDebited, currencyCode, state, submittedDate);
	}
}
