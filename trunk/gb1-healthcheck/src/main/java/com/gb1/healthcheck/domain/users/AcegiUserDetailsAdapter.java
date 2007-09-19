package com.gb1.healthcheck.domain.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.Validate;

/**
 * An adapter that allows the use of internal user objects as instances of the Acegi UserDetails
 * interface.
 * 
 * @author Guillaume Bilodeau
 */
public class AcegiUserDetailsAdapter implements UserDetails, Serializable {
	private User target;

	public AcegiUserDetailsAdapter(User target) {
		Validate.notNull(target);
		this.target = target;
		this.target.getRoles().size();
	}

	public GrantedAuthority[] getAuthorities() {
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();

		for (Role role : target.getRoles()) {
			authoritySet.add(new AcegiGrantedAuthorityAdapter(role));
		}

		GrantedAuthority[] authorityArray = authoritySet.toArray(new GrantedAuthority[0]);

		return authorityArray;
	}

	public User getUser() {
		return target;
	}

	public String getPassword() {
		return target.getPassword();
	}

	public String getUsername() {
		return target.getLogin();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return target.isActive();
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return target.isActive();
	}

	public static class AcegiGrantedAuthorityAdapter implements GrantedAuthority, Serializable {
		private Role target;

		public AcegiGrantedAuthorityAdapter(Role target) {
			Validate.notNull(target);
			this.target = target;
		}

		public String getAuthority() {
			return target.getName();
		}
	}
}
