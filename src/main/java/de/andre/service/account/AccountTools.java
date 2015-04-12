package de.andre.service.account;

import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;

import java.util.Collection;

/**
 * Created by andreika on 4/12/2015.
 */
public interface AccountTools {
	DpsUser getUserById(Integer userId);

	void saveUser(DpsUser dpsUser);

	DpsUser findUserByEmail(String email);

	Collection<DpsAddress> findAddressesByUser(DpsUser dpsUser);
}
