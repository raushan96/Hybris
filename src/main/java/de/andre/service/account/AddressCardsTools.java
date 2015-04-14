package de.andre.service.account;

import de.andre.entity.core.DpsAddress;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei on 4/14/2015.
 */
public interface AddressCardsTools {
	DpsAddress getAddressById(String addressId);
}
