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
public class AddressTools {
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	private final AccountTools accountTools;

	@Autowired
	public AddressTools(final AddressRepository addressRepository, final UserRepository userRepository, final AccountTools accountTools) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.accountTools = accountTools;
	}

	@Transactional(readOnly = true)
	public DpsAddress getAddressById(final String addressId) {
		return addressRepository.findOne(Integer.valueOf(addressId));
	}

	@Transactional
	public void deleteAdressById(final String addressId) {
		addressRepository.delete(Integer.valueOf(addressId));
	}

	@Transactional
	public Integer createAddress(final DpsAddress dpsAddress) {
		dpsAddress.setDpsUser(accountTools.getCommerceUser());
		addressRepository.save(dpsAddress);
		return dpsAddress.getAddressId();
	}
}
