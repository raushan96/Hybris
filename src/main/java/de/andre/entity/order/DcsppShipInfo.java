package de.andre.entity.order;

import javax.persistence.*;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_SHIP_INFO", schema = "HYBRIS")
public class DcsppShipInfo {
    private Integer shippingInfoId;
    private Long version;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String state;
    private String country;
    private DcsppShipGroup shipGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
    @SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
    @Column(name = "SHIPPING_INFO_ID")
    public Integer getShippingInfoId() {
        return shippingInfoId;
    }

    public void setShippingInfoId(Integer shippingInfoId) {
        this.shippingInfoId = shippingInfoId;
    }

    @Version
    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @OneToOne
    @JoinColumn(name = "SHIPPING_INFO_ID")
    public DcsppShipGroup getShipGroup() {
        return shipGroup;
    }

    public void setShipGroup(DcsppShipGroup shipGroup) {
        this.shipGroup = shipGroup;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    @Column(name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        if (o == null || getClass() != o.getClass()) return false;

        DcsppShipInfo that = (DcsppShipInfo) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
