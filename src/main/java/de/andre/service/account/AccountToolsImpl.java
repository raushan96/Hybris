package de.andre.service.account;

import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsCreditCard;
import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by andreika on 4/12/2015.
 */

@Service
public class AccountToolsImpl implements AccountTools {

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public AccountToolsImpl(final UserRepository userRepository, final AddressRepository addressRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Transactional(readOnly = true)
	@Override
	public DpsUser getUserById(final Integer userId) {
		return userRepository.findOne(userId);
	}

	@Transactional
	@Override
	public void saveUser(final DpsUser dpsUser) {
		userRepository.save(dpsUser);
	}

	@Transactional(readOnly = true)
	@Override
	public DpsUser findUserByEmail(final String email) {
		return userRepository.findByLogin(email);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<DpsAddress> findAddressesByUser(final DpsUser dpsUser) {
		return addressRepository.findAddressesByCustomer(dpsUser);
	}

	@Transactional
	@Override
	public void updatePassword(final String email, final String newPassword) {
		final String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
		userRepository.updateUserPassword(email, hashedPassword);
	}

	@Transactional(readOnly = true)
	@Override
	public DpsCreditCard findCardByUser(final DpsUser dpsUser) {
		return userRepository.findCardByUser(dpsUser);
	}
}
