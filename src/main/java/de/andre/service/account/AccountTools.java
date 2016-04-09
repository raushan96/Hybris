package de.andre.service.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import de.andre.repository.profile.AddressRepository;
import de.andre.repository.profile.ProfileRepository;
import de.andre.service.emails.EmailService;
import de.andre.service.security.HybrisUser;
import de.andre.utils.AccountHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static de.andre.utils.HybrisConstants.FORGOT_PASSWORD_EMAIL;
import static de.andre.utils.HybrisConstants.WELCOME_EMAIL;


@Service
public class AccountTools {
    private static final Logger log = LoggerFactory.getLogger(AccountTools.class);

    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public AccountTools(final ProfileRepository profileRepository, final AddressRepository addressRepository, final BCryptPasswordEncoder bCryptPasswordEncoder,
                        final EmailService emailService, final ObjectMapper objectMapper) {
        this.profileRepository = profileRepository;
        this.addressRepository = addressRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @Transactional(readOnly = true)
    public Profile getProfileById(final Integer ProfileId) {
        return profileRepository.findOne(ProfileId);
    }

    @Transactional
    public void saveProfile(final Profile profile) {
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Profile findProfileByEmail(final String email) {
        return profileRepository.findByLogin(email);
    }

    @Transactional(readOnly = true)
    public Collection<Address> findAddressesByProfile(final Profile profile) {
//        final Collection<Address> profileAddresses = addressRepository.findAddressesByCustomer(profile);
        final Collection<Address> profileAddresses = Collections.emptyList();

        if (profileAddresses == null || profileAddresses.size() == 0) {
            log.debug("No addresses found for Profile " + profile.getId());
            return Collections.emptyList();
        }

        return profileAddresses;
    }

    @Transactional
    public void createProfile(final Profile Profile, final HttpServletRequest pRequest) {
        final String hashedPassword = bCryptPasswordEncoder.encode(Profile.getPassword());
        Profile.setPassword(hashedPassword);
        profileRepository.save(Profile);

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new HybrisUser(Profile.getEmail(), Profile.getPassword(), Collections.singleton(HybrisUser.Profile_AUTHORITY)),
                null,
                Collections.singleton(HybrisUser.Profile_AUTHORITY));
        auth.setDetails(new WebAuthenticationDetails(pRequest));
        SecurityContextHolder.getContext().setAuthentication(auth);

        final Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("Profile", Profile);

        try {
            emailService.sendEmail(WELCOME_EMAIL, templateParams, Profile.getEmail());
        } catch (final IOException | MessagingException e) {
            log.error("Email exception sending welcome email.", e);
        }
    }

    @Transactional
    public void updatePassword(final String email, final String newPassword) {
        final String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
        profileRepository.updateProfilePassword(email, hashedPassword);
    }

    @Transactional
    public ObjectNode forgotPassword(final String pEmail) {
        final ObjectNode response = objectMapper.createObjectNode();
        if (profileRepository.countByEmail(pEmail) < 1) {
            log.debug("Cannot find Profiles with {} email.", pEmail);
            return response
                    .put("success", "false")
                    .put("message", "Profile not found.");
        }

        final String randomPassword = AccountHelper.generateRandomString();
        final String hashedPassword = bCryptPasswordEncoder.encode(randomPassword);
        profileRepository.updateProfilePassword(pEmail, hashedPassword);

        final Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("profile", profileRepository.findByLogin(pEmail));
        templateParams.put("date", new Date());
        templateParams.put("password", randomPassword);

        try {
            emailService.sendEmail(FORGOT_PASSWORD_EMAIL, templateParams, pEmail);
        } catch (final IOException | MessagingException e) {
            log.error("Email exception sending forgot password email.", e);
        }
        return response.put("success", "true");
    }

    public Profile getCommerceProfile() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof HybrisUser) {
                return ((HybrisUser) principal).getCommerceProfile();
            }

            log.warn("Unknown principal instance.");
            return null;
        }

        log.warn("Profile is not authorized, returning null.");
        return null;
    }

    public void updateCommerceProfile(final Profile profile) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof HybrisUser) {
                ((HybrisUser) principal).setCommerceProfile(profile);
            } else {
                log.warn("Unknown principal instance, cannot update.");
            }
        }
    }

    public boolean isSoftLogged() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }
}
