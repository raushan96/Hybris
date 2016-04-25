package de.andre.service.account;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import de.andre.repository.profile.AddressRepository;
import de.andre.repository.profile.ProfileAdapterRepository;
import de.andre.repository.profile.ProfileRepository;
import de.andre.utils.HybrisConstants;
import de.andre.utils.ProfileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static de.andre.utils.HybrisConstants.ADDRESS_NAME_PREFIX;
import static de.andre.utils.HybrisConstants.DEFAULT_SHIPPING_NAME;

public class AddressTools {
    private static final Logger logger = LoggerFactory.getLogger(AddressTools.class);

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
        final Profile profile = profileRepository.profileWithAddresses(ProfileHelper.currentProfileId())
                .orElseThrow(() -> new IllegalStateException("Profile was removed"));
        return profile.getAddresses().get(addressName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public int deleteAddressByName(final String addressName) {
        return addressRepository.deleteProfileAddress(ProfileHelper.currentProfileId(), addressName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public String changeDefaultShipping(final String addressName) {
        if (!StringUtils.hasLength(addressName) || DEFAULT_SHIPPING_NAME.equals(addressName)) {
            logger.debug("Not eligible address name '{}'", addressName);
            return HybrisConstants.EMPTY_STRING;
        }

        final Profile profile = profileTools.currentProfile();
        final String newNickname = generateNickname(addressName, profile);

        final Address oldDefaultShipping = profile.getAddresses().remove(DEFAULT_SHIPPING_NAME);
        Assert.notNull(oldDefaultShipping);
        final Address newDefaultShipping = profile.getAddresses().remove(addressName);
        Assert.notNull(newDefaultShipping);

        logger.debug("Making {} address as default shipping", addressName);
        newDefaultShipping.setAddressName(DEFAULT_SHIPPING_NAME);
        profile.addAddress(newDefaultShipping);
        oldDefaultShipping.setAddressName(newNickname);
        profile.addAddress(oldDefaultShipping);

        return newNickname;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public String addSecondaryAddress(final Address address) {
        final Profile profile = profileTools.currentProfile();
        final String nickname = generateNickname(null, profile);

        address.setAddressName(nickname);
        profile.getAddresses().put(nickname, address);
        address.setProfile(profile);
        profileRepository.save(profile);

        return nickname;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public String updateSecondaryAddress(final Address address) {
        final Profile profile = profileTools.currentProfile();

        final Address currentAddress = profile.getAddresses().get(address.getAddressName());
        Assert.notNull(currentAddress, "Updating address must exist");
        BeanUtils.copyProperties(address, currentAddress, "id", "addressName", "profile");

        profileRepository.save(profile);

        return address.getAddressName();
    }

    private String generateNickname(final String providedNickname, final Profile profile) {
        Assert.notNull(profile);
        final Set<String> existingNicknames = profileTools.addressesByProfile(profile.getId())
                .stream()
                .map(Address::getAddressName)
                .collect(Collectors.toSet());
        return ProfileHelper.generateUniqueNickname(
                providedNickname,
                ADDRESS_NAME_PREFIX,
                existingNicknames
        );
    }

    private boolean isDefaultShipping(final Address address) {
        return address != null && DEFAULT_SHIPPING_NAME.equals(address.getAddressName());
    }
}
