package de.andre.service.security;

import de.andre.entity.profile.Profile;
import de.andre.service.account.ProfileTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final ProfileTools profileTools;

    public UserDetailsServiceImpl(final ProfileTools profileTools) {
        this.profileTools = profileTools;
    }

    @Override
    public UserDetails loadUserByUsername(final String pEmail) throws UsernameNotFoundException {
        final Profile profile = profileTools.profileByEmail(pEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Profile wasn\'t found with the email: " + pEmail));

        if (logger.isDebugEnabled()) {
            logger.debug("Found profile with {} id and {} email", profile.getId(), profile.getEmail());
        }

        return new HybrisUser(profile.getEmail(), profile.getPassword(), profile);
    }
}
