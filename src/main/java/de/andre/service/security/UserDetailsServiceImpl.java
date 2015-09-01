package de.andre.service.security;

import de.andre.entity.core.DpsUser;
import de.andre.service.account.AccountTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by andreika on 3/15/2015.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	AccountTools accountTools;

	@Override
	public UserDetails loadUserByUsername(final String pEmail) throws UsernameNotFoundException {
		final DpsUser user = accountTools.findUserByEmail(pEmail);
		if (logger.isDebugEnabled()) {
			logger.debug("Found user with " + user.getUserId() + " id and " + user.getEmail() + " email.");
		}

		if (null == user) {
			throw new UsernameNotFoundException("User wasn't found with provided email: " + pEmail);
		}

		return new HybrisUser(user.getEmail(), user.getPassword(), user);
	}
}
