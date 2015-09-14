package de.andre.service.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static de.andre.utils.HybrisConstants.FORGOT_PASSWORD_EMAIL;
import static de.andre.utils.HybrisConstants.WELCOME_EMAIL;


@Service
public class AccountTools {

	private static final Logger log = LoggerFactory.getLogger(AccountTools.class);

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ObjectMapper objectMapper;
	private final EmailService emailService;

	@Autowired
	public AccountTools(final UserRepository userRepository, final AddressRepository addressRepository, final BCryptPasswordEncoder bCryptPasswordEncoder,
						final EmailService emailService, final ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.objectMapper = objectMapper;
		this.emailService = emailService;
	}

	@Transactional(readOnly = true)
	public DpsUser getUserById(final Integer userId) {
		return userRepository.findOne(userId);
	}

	@Transactional
	public void saveUser(final DpsUser dpsUser) {
		userRepository.save(dpsUser);
	}

	@Transactional(readOnly = true)
	public DpsUser findUserByEmail(final String email) {
		return userRepository.findByLogin(email);
	}

	@Transactional(readOnly = true)
	public Collection<DpsAddress> findAddressesByUser(final DpsUser dpsUser) {
		final Collection<DpsAddress> userAddresses = addressRepository.findAddressesByCustomer(dpsUser);

		if (userAddresses == null || userAddresses.size() == 0) {
			log.debug("No addresses found for user " + dpsUser.getUserId());
			return Collections.emptyList();
		}

		return userAddresses;
	}

	@Transactional
	public void createUser(final DpsUser dpsUser, final HttpServletRequest pRequest) {
		final String hashedPassword = bCryptPasswordEncoder.encode(dpsUser.getPassword());
		dpsUser.setPassword(hashedPassword);
		userRepository.save(dpsUser);

		final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				new HybrisUser(dpsUser.getEmail(), dpsUser.getPassword(), Collections.singleton(HybrisUser.USER_AUTHORITY)),
				null,
				Collections.singleton(HybrisUser.USER_AUTHORITY));
		auth.setDetails(new WebAuthenticationDetails(pRequest));
		SecurityContextHolder.getContext().setAuthentication(auth);

		final Map<String, Object> templateParams = new HashMap<>();
		templateParams.put("user", dpsUser);

		try {
			emailService.sendEmail(WELCOME_EMAIL, templateParams, dpsUser.getEmail());
		} catch (final IOException | MessagingException e) {
			log.error("Email exception sending welcome email.", e);
		}
	}

	@Transactional
	public void updatePassword(final String email, final String newPassword) {
		final String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
		userRepository.updateUserPassword(email, hashedPassword);
	}

	@Transactional
	public ObjectNode forgotPassword(final String pEmail) {
		final ObjectNode response = objectMapper.createObjectNode();
		if (userRepository.countByEmail(pEmail) < 1) {
			log.debug("Cannot find users with {} email.", pEmail);
			return response.put("success", "false").put("message", "User not found.");
		}

		final String randomPassword = AccountHelper.generateRandomString();
		final String hashedPassword = bCryptPasswordEncoder.encode(randomPassword);
		userRepository.updateUserPassword(pEmail, hashedPassword);

		final Map<String, Object> templateParams = new HashMap<>();
		templateParams.put("user", userRepository.findByLogin(pEmail));
		templateParams.put("date", new Date());
		templateParams.put("password", randomPassword);

		try {
			emailService.sendEmail(FORGOT_PASSWORD_EMAIL, templateParams, pEmail);
		} catch (final IOException | MessagingException e) {
			log.error("Email exception sending forgot password email.", e);
		}
		return response.put("success", "true");
	}

	public DpsUser getCommerceUser() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof HybrisUser) {
				return ((HybrisUser) principal).getCommerceUser();
			}

			log.warn("Unknown principal instance.");
			return null;
		}

		log.warn("User is not authorized, returning null.");
		return null;
	}

	public void updateCommerceUser(final DpsUser dpsUser) {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof HybrisUser) {
				((HybrisUser) principal).setCommerceUser(dpsUser);
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
