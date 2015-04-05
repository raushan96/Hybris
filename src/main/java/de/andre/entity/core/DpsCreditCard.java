package de.andre.entity.core;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_CREDIT_CARD", schema = "HYBRIS")
public class DpsCreditCard {
	private Integer creditId;
	private String creditCardNumber;
	private Date expirationDate;
	private DpsUser dpsUser;
	private DpsAddress dpsAddress;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
	@SequenceGenerator(name = "profileSeq", sequenceName = "profile_seq",  allocationSize = 10)
	@Column(name = "CREDIT_ID")
	public Integer getCreditId() {
		return creditId;
	}

	public void setCreditId(Integer creditId) {
		this.creditId = creditId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public DpsUser getDpsUser() {
		return dpsUser;
	}

	public void setDpsUser(DpsUser dpsUser) {
		this.dpsUser = dpsUser;
	}

	@OneToOne
	@JoinColumn(name = "BILLING_ADDR", referencedColumnName = "ADDRESS_ID")
	public DpsAddress getDpsAddress() {
		return dpsAddress;
	}

	public void setDpsAddress(DpsAddress dpsAddress) {
		this.dpsAddress = dpsAddress;
	}

	@NotBlank
	@CreditCardNumber
	@Column(name = "CREDIT_CARD_NUMBER")
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	@NotBlank
	@Column(name = "EXPIRATION_DATE")
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DpsCreditCard)) return false;

		DpsCreditCard that = (DpsCreditCard) o;

		if (!creditCardNumber.equals(that.creditCardNumber)) return false;
		if (!expirationDate.equals(that.expirationDate)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creditCardNumber, expirationDate);
	}
}
