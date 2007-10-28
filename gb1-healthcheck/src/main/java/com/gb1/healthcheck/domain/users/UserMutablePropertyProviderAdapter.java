package com.gb1.healthcheck.domain.users;

import java.util.Set;

public class UserMutablePropertyProviderAdapter implements UserMutablePropertyProvider {
	private UserUpdateRequest request;

	public UserMutablePropertyProviderAdapter(UserUpdateRequest request) {
		this.request = request;
	}

	public String getEmail() {
		return request.getEmail();
	}

	public Set<Role> getRoles() {
		return request.getRoles();
	}
}
