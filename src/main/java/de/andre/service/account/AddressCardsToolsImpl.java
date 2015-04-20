package de.andre.service.account;

import de.andre.entity.core.DpsAddress;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei on 4/14/2015.
 */

@Service
public class AddressCardsToolsImpl implements AddressCardsTools {
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	@Autowired
	public AddressCardsToolsImpl(AddressRepository addressRepository, UserRepository userRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public DpsAddress getAddressById(String addressId) {
		return addressRepository.findOne(Integer.valueOf(addressId));
	}

	@Transactional
	@Override
	public void deleteAdressById(String addressId) {
		addressRepository.delete(Integer.valueOf(addressId));
	}

	@Transactional
	@Override
	public void createAddress(DpsAddress dpsAddress, String userId) {
		dpsAddress.setDpsUser(userRepository.getOne(Integer.valueOf(userId)));
		addressRepository.save(dpsAddress);
	}
}
