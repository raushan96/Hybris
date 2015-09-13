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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	AccountTools accountTools;

	@Override
	public UserDetails loadUserByUsername(final String pEmail) throws UsernameNotFoundException {
		final DpsUser user = accountTools.findUserByEmail(pEmail);
		if (log.isDebugEnabled()) {
			log.debug("Found user with {} id and {} email", user.getUserId(), user.getEmail());
		}

		if (null == user) {
			throw new UsernameNotFoundException("User wasn't found with provided email: " + pEmail);
		}

		return new HybrisUser(user.getEmail(), user.getPassword(), user);
	}
}
