package com.gb1.healthcheck.services.users;

import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserPropertyProvider;
import com.gb1.healthcheck.domain.users.UserRegistrationRequest;

public class UserPropertyProviderAdapter implements UserPropertyProvider {
	private UserRegistrationRequest request;

	public UserPropertyProviderAdapter(UserRegistrationRequest req) {
		this.request = req;
	}

	public String getLogin() {
		return request.getLogin();
	}

	public String getPassword() {
		return request.getPassword();
	}

	public String getEmail() {
		return request.getEmail();
	}

	public Set<Role> getRoles() {
		return request.getRoles();
	}
}
