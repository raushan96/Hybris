package de.andre.web.beans.account;

import de.andre.entity.core.DpsUser;
import de.andre.service.account.AccountTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by andreika on 5/3/2015.
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfileHolder {
	@Autowired
	private AccountTools accountTools;

	private DpsUser profile;

	public DpsUser getUserFromRequest() {
		DpsUser sessionUser = null;
		final ServletRequestAttributes reqAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null != reqAttr) {
			sessionUser = (DpsUser) reqAttr.getRequest().getSession(true).getAttribute("dpsUser");
			if (null == sessionUser) {
				final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (null != auth) {
					final String username = auth.getPrincipal() instanceof UserDetails ? ((UserDetails) auth.getPrincipal()).getUsername() : null;
					sessionUser = accountTools.findUserByEmail(username);
					if (null != sessionUser) {
						reqAttr.getRequest().getSession().setAttribute("dpsuser", sessionUser);
					}
				}
			}
		}
		return sessionUser;
	}

	public DpsUser getProfile() {
		if (null == profile) {
			this.profile = getUserFromRequest();
		}
		return profile;
	}

	public void setProfile(DpsUser profile) {
		this.profile = profile;
	}
}
