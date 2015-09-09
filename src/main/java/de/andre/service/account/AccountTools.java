package de.andre.service.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsCreditCard;
import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import de.andre.service.security.HybrisUser;
import de.andre.utils.AccountHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by andreika on 4/12/2015.
 */

@Service
public class AccountTools {

	private static final Logger log = LoggerFactory.getLogger(AccountTools.class);

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ObjectMapper objectMapper;

	@Autowired
	private EmailService emailService;

	@Autowired
	public AccountTools(final UserRepository userRepository, final AddressRepository addressRepository, final BCryptPasswordEncoder bCryptPasswordEncoder,
						final ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.objectMapper = objectMapper;
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
	public void updatePassword(final String email, final String newPassword) {
		final String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
		userRepository.updateUserPassword(email, hashedPassword);
	}

	@Transactional
	public ObjectNode forgotPassword(final String pEmail) {
		ObjectNode response = objectMapper.createObjectNode();
		if (userRepository.countByEmail(pEmail) < 1) {
			log.debug("Cannot find users with {0} email.", pEmail);
			return response.put("result", "User not found.");
		}

		final String randomPassword = AccountHelper.generateRandomString();
		final String hashedPassword = bCryptPasswordEncoder.encode(randomPassword);
		userRepository.updateUserPassword(pEmail, hashedPassword);
//		emailService.sendSimpleEmail();
		return response.put("result", "success");
	}

	@Transactional(readOnly = true)
	public DpsCreditCard findCardByUser(final DpsUser dpsUser) {
		return userRepository.findCardByUser(dpsUser);
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

	public boolean isSoftLogged() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
	}
}
