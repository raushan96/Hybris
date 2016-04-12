package de.andre.entity.profile;

import com.fasterxml.jackson.annotation.JsonView;
import de.andre.entity.dto.View;
import de.andre.entity.enums.State;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "hp_address", schema = "hybris")
public class Address {
    private Long id;
    private String addressName;
    private String city;
    private String postalCode;
    private String countryCode;
    private String address;
    private State state;

    private String oldAddressName;
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

    @ManyToOne(fetch = FetchType.LAZY)
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @JsonView(View.AddressView.class)
    @Column(name = "address_name")
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @JsonView(View.AddressView.class)
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonView(View.AddressView.class)
    @NotBlank
    @Length(min = 3, max = 15)
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonView(View.AddressView.class)
    @NotBlank
    @Length(min = 2, max = 2)
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonView(View.AddressView.class)
    @NotBlank
    @Length(min = 5, max = 80)
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonView(View.AddressView.class)
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Transient
    public String getOldAddressName() {
        return oldAddressName;
    }

    public void setOldAddressName(String oldAddressName) {
        this.oldAddressName = oldAddressName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (!addressName.equals(address1.addressName)) return false;
        if (!city.equals(address1.city)) return false;
        if (!postalCode.equals(address1.postalCode)) return false;
        if (!countryCode.equals(address1.countryCode)) return false;
        if (!address.equals(address1.address)) return false;
        return state == address1.state;

    }

    @Override
    public int hashCode() {
        int result = addressName.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + postalCode.hashCode();
        result = 31 * result + countryCode.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }
}
