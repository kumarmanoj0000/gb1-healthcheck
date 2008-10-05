package com.gb1.healthcheck.domain.users;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.healthcheck.core.Token;

/**
 * A user activation request.
 * 
 * @author Guillaume Bilodeau
 */
public class UserActivationRequest {
	private User pendingUser;
	private Token activationToken;

	public UserActivationRequest(User user, Token activationToken) {
		this.pendingUser = user;
		this.activationToken = activationToken;
	}

	public User getPendingUser() {
		return pendingUser;
	}

	public Token getActivationToken() {
		return activationToken;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UserActivationRequest)) {
			return false;
		}

		UserActivationRequest that = (UserActivationRequest) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.pendingUser, that.pendingUser)
				.append(this.activationToken, that.activationToken);

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(pendingUser).append(activationToken);
		return builder.toHashCode();
	}
}
