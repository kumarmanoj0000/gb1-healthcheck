package com.gb1.healthcheck.domain.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * An adapter that allows the use of internal user objects as instances of the Spring Security
 * UserDetails interface.
 * 
 * @author Guillaume Bilodeau
 */
public class SpringUserDetailsAdapter implements UserDetails, Serializable {
	private User target;

	public SpringUserDetailsAdapter(User target) {
		Validate.notNull(target);
		this.target = target;
		this.target.getRoles().size();
	}

	public GrantedAuthority[] getAuthorities() {
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();

		for (Role role : target.getRoles()) {
			authoritySet.add(new SpringGrantedAuthorityAdapter(role));
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

	@Override
	public String toString() {
		return target.getLogin();
	}

	public static class SpringGrantedAuthorityAdapter implements GrantedAuthority, Serializable {
		private Role target;

		public SpringGrantedAuthorityAdapter(Role target) {
			this.target = target;
		}

		public String getAuthority() {
			return target.getName();
		}

		@Override
		public String toString() {
			return target.getName();
		}

		public int compareTo(Object o) {
			SpringGrantedAuthorityAdapter that = (SpringGrantedAuthorityAdapter) o;
			CompareToBuilder builder = new CompareToBuilder().append(this.getAuthority(), that
					.getAuthority());
			return builder.toComparison();
		}
	}
}
