package de.andre.entity.core;

import javax.persistence.*;

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
    private String country;
    private DpsUser dpsUser;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
    @SequenceGenerator(name="profileSeq", sequenceName = "profile_seq")
    @Column(name = "ADDRESS_ID")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
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
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DpsAddress)) return false;

        DpsAddress that = (DpsAddress) o;

        if (!addressId.equals(that.addressId)) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addressId.hashCode();
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
