package de.andre.service.account;

import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

	@Autowired
	public AccountToolsImpl(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public DpsUser getUserById(Integer userId) {
		return userRepository.findOne(userId);
	}

	@Transactional
	@Override
	public void saveUser(DpsUser dpsUser) {
		userRepository.save(dpsUser);
	}

	@Transactional(readOnly = true)
	@Override
	public DpsUser findUserByEmail(String email) {
		return userRepository.findByLogin(email);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<DpsAddress> findAddressesByUser(DpsUser dpsUser) {
		return addressRepository.findAddressesByCustomer(dpsUser);
	}

	@Transactional
	@Override
	public void updatePassword(String email, String newPassword) {
		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		userRepository.updateUserPassword(email, newPassword);
	}
}
