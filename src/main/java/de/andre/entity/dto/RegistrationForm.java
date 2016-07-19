package de.andre.entity.dto;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;

import javax.validation.Valid;

public class RegistrationForm {
    private @Valid Profile profile;
    private @Valid Address shippingAddress;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
