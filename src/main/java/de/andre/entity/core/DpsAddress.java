package de.andre.entity.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_USER_ADDRESS", schema = "HYBRIS")
public class DpsAddress {
	private Integer addressId;
	private String companyName;
	private String city;
	private String postalCode;
	private String address;
	private String country;
	private DpsUser dpsUser;

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
	@JoinColumn(name = "USER_ID")
	public DpsUser getDpsUser() {
		return dpsUser;
	}

	public void setDpsUser(DpsUser dpsUser) {
		this.dpsUser = dpsUser;
	}

	@Column(name = "COMPANY_NAME")
	@Length(min = 3, max = 20)
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

	@Column(name = "COUNTRY")
	@Length(min = 3, max = 20)
	@NotEmpty
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DpsAddress)) return false;

		DpsAddress that = (DpsAddress) o;

		if (city != null ? !city.equals(that.city) : that.city != null) return false;
		if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
		if (country != null ? !country.equals(that.country) : that.country != null) return false;
		if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
		if (address != null ? !address.equals(that.address) : that.address != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyName, city, postalCode, country, address);
	}
}
