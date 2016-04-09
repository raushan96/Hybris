package de.andre.service.account;

import de.andre.entity.profile.Address;
import de.andre.repository.profile.AddressRepository;
import de.andre.repository.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei on 4/14/2015.
 */

@Service
public class AddressTools {
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final AccountTools accountTools;

    @Autowired
    public AddressTools(final AddressRepository addressRepository, final ProfileRepository profileRepository, final AccountTools accountTools) {
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.accountTools = accountTools;
    }

    @Transactional(readOnly = true)
    public Address getAddressById(final String addressId) {
        return addressRepository.findOne(Integer.valueOf(addressId));
    }

    @Transactional
    public void deleteAdressById(final String addressId) {
        addressRepository.delete(Integer.valueOf(addressId));
    }

    @Transactional
    public Long createAddress(final Address address) {
        address.setProfile(accountTools.getCommerceProfile());
        addressRepository.save(address);
        return address.getId();
    }
}
