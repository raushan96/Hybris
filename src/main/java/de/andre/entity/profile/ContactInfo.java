package de.andre.entity.profile;

import com.fasterxml.jackson.annotation.JsonView;
import de.andre.entity.dto.View;
import de.andre.entity.enums.State;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ContactInfo implements Cloneable {
    private String city;
    private String postalCode;
    private String countryCode;
    private String address;
    private State state;

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

        ContactInfo that = (ContactInfo) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return state == that.state;

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public ContactInfo clone() {
        try {
            return (ContactInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
