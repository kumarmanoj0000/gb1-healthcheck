package com.gb1.healthcheck.web.users;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserUpdateRequest;

public class BasicUserUpdateRequest implements UserUpdateRequest {
	private String login;
	private String email;
	private Set<Role> roles = new HashSet<Role>();

	public BasicUserUpdateRequest(User user) {
		Validate.notNull(user);

		login = user.getLogin();
		email = user.getEmail();
	}

	public String getLogin() {
		return login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}
}
