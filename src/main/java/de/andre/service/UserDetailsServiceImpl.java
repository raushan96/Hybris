package de.andre.service;

import de.andre.entity.core.DpsUser;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by andreika on 3/15/2015.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            DpsUser user = userRepository.findByLogin(email);
            if (null == user) {
                throw new UsernameNotFoundException("User wasnt found with provided email: " + email);
            }
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new User(user.getEmail(), user.getPassword(), roles);
        } catch (UsernameNotFoundException ue) {
            ue.printStackTrace();
        }

        return null;
    }
}
