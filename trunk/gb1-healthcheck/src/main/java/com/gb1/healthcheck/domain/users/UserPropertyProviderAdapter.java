package com.gb1.healthcheck.domain.users;

import java.util.Set;


public class UserPropertyProviderAdapter implements UserPropertyProvider {
	private UserRegistrationRequest request;

	public UserPropertyProviderAdapter(UserRegistrationRequest request) {
		this.request = request;
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
