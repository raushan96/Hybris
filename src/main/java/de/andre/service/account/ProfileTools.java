package de.andre.service.account;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Interest;
import de.andre.entity.profile.Profile;
import de.andre.entity.profile.WishList;
import de.andre.repository.profile.AddressRepository;
import de.andre.repository.profile.ProfileAdapterRepository;
import de.andre.repository.profile.ProfileRepository;
import de.andre.service.security.HybrisUser;
import de.andre.utils.BeanHelper;
import de.andre.utils.HybrisConstants;
import de.andre.utils.ProfileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.util.*;
import java.util.stream.Collectors;

import static de.andre.utils.HybrisConstants.DEFAULT_SHIPPING_NAME;

public class ProfileTools {
    private static final Logger logger = LoggerFactory.getLogger(ProfileTools.class);

    private final ProfileAdapterRepository profileAdapterRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    private Set<String> profileEditableProps;

    public Set<String> getProfileEditableProps() {
        return Collections.unmodifiableSet(profileEditableProps);
    }

    public void setProfileEditableProps(Set<String> profileEditableProps) {
        this.profileEditableProps = profileEditableProps;
    }

    public ProfileTools(final ProfileAdapterRepository profileAdapterRepository, final PasswordEncoder passwordEncoder) {
        this.profileAdapterRepository = profileAdapterRepository;
        this.profileRepository = profileAdapterRepository.getProfileRepository();
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Profile profileById(final Long profileId) {
        return profileRepository.findOne(profileId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public void updateProfile(final Profile newProfile) {
        final Profile currentProfile = profileByEmail(ProfileHelper.authenticatedProfile().getEmail())
                .orElseThrow(() -> new IllegalStateException("Profile was removed"));
        synchronized (RequestContextHolder.getRequestAttributes().getSessionMutex()) {
            BeanHelper.copyBeanProperties(newProfile, currentProfile, profileEditableProps);

            profileRepository.save(currentProfile);

            updateCommerceProfile(currentProfile);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Profile> profileByEmail(final String email) {
        if (StringUtils.isEmpty(email)) {
            return Optional.empty();
        }
        return profileRepository.findByLogin(email);
    }

    @Transactional(readOnly = true)
    public List<Address> addressesByProfile(final Profile profile) {
        final List<Address> profileAddresses = profileAdapterRepository.getAddressRepository().findAllAddresses(profile);
        if (profile.getShippingAddress() != null) {
            profileAddresses.add(profile.getShippingAddress());
        }

        if (profileAddresses.size() == 0) {
            logger.debug("No addresses found for Profile " + profile.getId());
            return Collections.emptyList();
        }

        return profileAddresses;
    }

    @Transactional
    public void createProfile(final Profile profile, final String[] interests) {
        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            final Address shippingAddress = profile.getShippingAddress();
            if (!StringUtils.hasText(shippingAddress.getAddressName())) {
                shippingAddress.setAddressName(DEFAULT_SHIPPING_NAME);
            }
            profileAdapterRepository.getAddressRepository().save(shippingAddress);

            profile.setPassword(passwordEncoder.encode(profile.getPassword()));
            addWishList(profile);
            profile.setInterests(resolveChosenInterests(interests));

            profileRepository.save(profile);

            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new HybrisUser(profile.getEmail(), profile.getPassword(), profile),
                    null,
                    Collections.singleton(HybrisUser.USER_AUTHORITY));
            auth.setDetails(new WebAuthenticationDetails(
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    private Set<Interest> resolveChosenInterests(final String[] codes) {
        if (codes == null || codes.length == 0) {
            return Collections.emptySet();
        }

        final Set<Integer> validCodes = Arrays.stream(codes)
                .map(code -> {
                    try {
                        return Integer.parseInt(code);
                    } catch (NumberFormatException ex) {
                        return -1;
                    }
                })
                .collect(Collectors.toSet());

        return !validCodes.isEmpty() ?
                profileAdapterRepository.getInterestRepository().interestsByCodes(validCodes) :
                Collections.emptySet();
    }

    private void addWishList(final Profile profile) {
        final WishList wishList = new WishList();
        wishList.setProfile(profile);
        profile.setWishList(wishList);
        wishList.setShippingAddress(profile.getShippingAddress());
    }

    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    public void updatePassword(final String newPassword) {
        final String hashedPassword = passwordEncoder.encode(newPassword);
        profileRepository.updateProfilePassword(ProfileHelper.authenticatedProfile().getEmail(), hashedPassword);
    }

    @Transactional(readOnly = true)
    public List<Interest> availableInterests() {
        return profileAdapterRepository.getInterestRepository().findAll();
    }

    @Transactional
    public void forgotPassword(final String pEmail) {
        if (profileRepository.countByEmail(pEmail) < 1) {
            logger.debug("Cannot find Profiles with {} email.", pEmail);
        }

        final String randomPassword = ProfileHelper.generateRandomString();
        final String hashedPassword = passwordEncoder.encode(randomPassword);
        profileRepository.updateProfilePassword(pEmail, hashedPassword);
    }

    public void updateCommerceProfile(final Profile profile) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof HybrisUser) {
                ((HybrisUser) principal).setCommerceProfile(profile);
            } else {
                logger.warn("Unknown principal instance, cannot update.");
            }
        }
    }
}
