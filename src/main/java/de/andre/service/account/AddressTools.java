package de.andre.service.account;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import de.andre.repository.profile.AddressRepository;
import de.andre.repository.profile.ProfileAdapterRepository;
import de.andre.repository.profile.ProfileRepository;
import de.andre.utils.ProfileHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static de.andre.utils.HybrisConstants.ADDRESS_NAME_PREFIX;

public class AddressTools {
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final ProfileTools profileTools;

    public AddressTools(final ProfileAdapterRepository profileAdapterRepository, final ProfileTools profileTools) {
        this.addressRepository = profileAdapterRepository.getAddressRepository();
        this.profileRepository = profileAdapterRepository.getProfileRepository();
        this.profileTools = profileTools;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional(readOnly = true)
    public Address addressByName(final String addressName) {
        final Profile profile = profileRepository.profileWithAddresses(ProfileHelper.authenticatedProfile().getId())
                .orElseThrow(() -> new IllegalStateException("Profile was removed"));
        return profile.getAddresses().get(addressName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public int deleteAddressByName(final String addressName) {
        return addressRepository.deleteProfileAddress(ProfileHelper.authenticatedProfile(), addressName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public String addSecondaryAddress(final Address address) {
        final Profile profile = profileTools.profileById(ProfileHelper.authenticatedProfile().getId());

        final Set<String> existingNicknames = profileTools.addressesByProfile(profile)
                .stream()
                .filter(addr -> !addr.getAddressName().equals(address.getAddressName()))
                .map(Address::getAddressName)
                .collect(Collectors.toSet());
        final String nickname = ProfileHelper.generateUniqueNickname(
                null,
                ADDRESS_NAME_PREFIX,
                existingNicknames
        );

        address.setAddressName(nickname);
        profile.getAddresses().put(nickname, address);
        address.setProfile(profile);
        profileRepository.save(profile);

        return nickname;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public String updateSecondaryAddress(final Address address) {
        final Profile profile = profileTools.profileById(ProfileHelper.authenticatedProfile().getId());

        final Address currentAddress = profile.getAddresses().get(address.getAddressName());
        Assert.notNull(currentAddress, "Updating address must exist");
        BeanUtils.copyProperties(address, currentAddress, "id", "addressName", "profile");

        profileRepository.save(profile);

        return address.getAddressName();
    }
}
