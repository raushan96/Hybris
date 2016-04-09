package de.andre.service.security;

import de.andre.entity.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class HybrisUser extends User {
    public static final GrantedAuthority Profile_AUTHORITY = new SimpleGrantedAuthority("ROLE_Profile");
    private static final long serialVersionUID = 4728380174114325367L;

    private final AtomicReference<Profile> commerceProfile = new AtomicReference<>();

    public HybrisUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public HybrisUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * Saves dps Profile in context with single Profile role, to retrieve it if needed in app code
     */
    public HybrisUser(String username, String password, Profile profile) {
        super(username, password, Collections.singleton(Profile_AUTHORITY));
        this.commerceProfile.set(profile);
    }

    public Profile getCommerceProfile() {
        return commerceProfile.get();
    }

    public boolean setCommerceProfile(Profile commerceProfile) {
        return this.commerceProfile.compareAndSet(getCommerceProfile(), commerceProfile);
    }
}
