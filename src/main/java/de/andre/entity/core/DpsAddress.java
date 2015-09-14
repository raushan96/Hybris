package de.andre.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.andre.entity.enums.UState;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DPS_USER_ADDRESS", schema = "HYBRIS")
public class DpsAddress {
	private Integer addressId;
	private String companyName;
	private String city;
	private String postalCode;
	private String address;
	private String countryCode;
	private DpsUser dpsUser;
	private UState state;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
	@SequenceGenerator(name = "profileSeq", sequenceName = "profile_seq",  allocationSize = 10)
	@Column(name = "ADDRESS_ID")
	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "USER_ID")
	public DpsUser getDpsUser() {
		return dpsUser;
	}

	public void setDpsUser(DpsUser dpsUser) {
		this.dpsUser = dpsUser;
	}

	@Column(name = "COMPANY_NAME")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "CITY")
	@NotEmpty
	@Length(min = 3, max = 20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "POSTAL_CODE")
	@NotEmpty
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "COUNTRY_CODE")
	@NotEmpty
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String country) {
		this.countryCode = country;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "STATE")
	@Enumerated(EnumType.ORDINAL)
	public UState getState() {
		return state;
	}

	public void setState(UState state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DpsAddress)) return false;

		DpsAddress that = (DpsAddress) o;

		if (city != null ? !city.equals(that.city) : that.city != null) return false;
		if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
		if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
		if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
		if (address != null ? !address.equals(that.address) : that.address != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyName, city, postalCode, countryCode, address);
	}
}
