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
public class Address extends ProfileBaseEntity {
    private String addressName;
    private String displayName;
    private String city;
    private String postalCode;
    private String countryCode;
    private String address;
    private State state;

    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (addressName != null ? !addressName.equals(address1.addressName) : address1.addressName != null)
            return false;
        if (displayName != null ? !displayName.equals(address1.displayName) : address1.displayName != null)
            return false;
        if (city != null ? !city.equals(address1.city) : address1.city != null) return false;
        if (postalCode != null ? !postalCode.equals(address1.postalCode) : address1.postalCode != null) return false;
        if (countryCode != null ? !countryCode.equals(address1.countryCode) : address1.countryCode != null)
            return false;
        if (address != null ? !address.equals(address1.address) : address1.address != null) return false;
        return state == address1.state;

    }

    @Override
    public int hashCode() {
        int result = addressName != null ? addressName.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
