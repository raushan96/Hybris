package de.andre.service.security;

import de.andre.entity.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class HybrisUser extends User {
    public static final Collection<? extends GrantedAuthority> USER_AUTHORITY =
            Collections.singleton(new SimpleGrantedAuthority("USER"));
    private static final long serialVersionUID = 4728380174114325367L;

    private final AtomicReference<UserIdentity> userIdentity = new AtomicReference<>();

    public HybrisUser(final String username, final String password, final Profile profile) {
        super(username, password, USER_AUTHORITY);
        Assert.notNull(profile);
        this.userIdentity.set(new UserIdentity(profile.getId(), profile.getEmail()));
    }

    public UserIdentity getUserIdentity() {
        return userIdentity.get();
    }

    public static class UserIdentity {
        private final Long id;
        private final String email;

        public UserIdentity(final Long id, final String email) {
            if (id == null || !StringUtils.hasLength(email)) {
                throw new IllegalArgumentException("Invalid params for new identity: " + id + " id and " + email + " email");
            }

            this.id = id;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof UserIdentity) {
                final UserIdentity otherIdentity = (UserIdentity) obj;
                return id.equals(otherIdentity.getId()) && email.equals(otherIdentity.getEmail());
            }

            return false;
        }

        @Override
        public int hashCode() {
            return id.hashCode() + email.hashCode();
        }

        @Override
        public String toString() {
            return String.format("User identity has %d id and %s email", id, email);
        }
    }
}
