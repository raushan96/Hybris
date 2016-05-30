package de.andre.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.andre.entity.dto.View;
import de.andre.entity.enums.State;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "hp_address", schema = "hybris")
public class Address extends ProfileBaseEntity {
    private String addressName;
    private String displayName;

    private Profile profile;
    private ContactInfo contactInfo;

    public Address() {
    }

    @JsonCreator
    public Address(@JsonProperty("displayName") String displayName,
            @JsonProperty("contactInfo") ContactInfo contactInfo) {
        this.displayName = displayName;
        this.contactInfo = contactInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id")
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
    @JsonProperty("contactInfo")
    @NotNull
    @Embedded
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (addressName != null ? !addressName.equals(address.addressName) : address.addressName != null) return false;
        if (displayName != null ? !displayName.equals(address.displayName) : address.displayName != null) return false;
        return contactInfo != null ? contactInfo.equals(address.contactInfo) : address.contactInfo == null;

    }

    @Override
    public int hashCode() {
        int result = addressName != null ? addressName.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        return result;
    }
}
