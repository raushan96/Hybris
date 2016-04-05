package de.andre.service.security;

import de.andre.entity.core.DpsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class HybrisUser extends User {

	public static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");
	private static final long serialVersionUID = 4728380174114325367L;

	private final AtomicReference<DpsUser> commerceUser = new AtomicReference<>();

	public HybrisUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public HybrisUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	/**
	 * Saves dps user in context with single user role, to retrieve it if needed in app code
	 */
	public HybrisUser(String username, String password, DpsUser dpsUser) {
		super(username, password, Collections.singleton(USER_AUTHORITY));
		this.commerceUser.set(dpsUser);
	}

	public DpsUser getCommerceUser() {
		return commerceUser.get();
	}

	public boolean setCommerceUser(DpsUser commerceUser) {
		return this.commerceUser.compareAndSet(getCommerceUser(), commerceUser);
	}
}
