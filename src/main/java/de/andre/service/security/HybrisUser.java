package de.andre.service.security;

import de.andre.entity.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class HybrisUser extends User {
    public static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("USER");
    private static final long serialVersionUID = 4728380174114325367L;

    private final AtomicReference<Profile> commerceProfile = new AtomicReference<>();

    public HybrisUser(final String username, final String password, final Profile profile) {
        super(username, password, Collections.singleton(USER_AUTHORITY));
        this.commerceProfile.set(profile);
    }

    public Profile getCommerceProfile() {
        return commerceProfile.get();
    }

    public boolean setCommerceProfile(final Profile commerceProfile) {
        return this.commerceProfile.compareAndSet(getCommerceProfile(), commerceProfile);
    }
}
