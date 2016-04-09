package de.andre.entity.profile;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "hp_address", schema = "hybris")
public class Address {
    private Long id;
    private String companyName;
    private String city;
    private String postalCode;
    private String countryCode;
    private String address;
    private Integer state;

    private Profile profile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Length(min = 3, max = 50)
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotBlank
    @Length(min = 3, max = 15)
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @NotBlank
    @Length(min = 2, max = 2)
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @NotBlank
    @Length(min = 5, max = 80)
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address hpAddress = (Address) o;

        if (companyName != null ? !companyName.equals(hpAddress.companyName) : hpAddress.companyName != null)
            return false;
        if (city != null ? !city.equals(hpAddress.city) : hpAddress.city != null) return false;
        if (postalCode != null ? !postalCode.equals(hpAddress.postalCode) : hpAddress.postalCode != null) return false;
        if (countryCode != null ? !countryCode.equals(hpAddress.countryCode) : hpAddress.countryCode != null)
            return false;
        if (address != null ? !address.equals(hpAddress.address) : hpAddress.address != null) return false;
        if (state != null ? !state.equals(hpAddress.state) : hpAddress.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
