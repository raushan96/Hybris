package de.andre.service.account;

import de.andre.entity.core.DpsAddress;

/**
 * Created by Andrei on 4/14/2015.
 */
public interface AddressCardsTools {
	DpsAddress getAddressById(String addressId);

	void deleteAdressById(String addressId);

	void createAddress(DpsAddress dpsAddress, String userId);
}
