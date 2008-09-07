package com.gb1.healthcheck.services.users;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.User;

public class UserAssemblerImpl implements UserAssembler {
	public UserAssemblerImpl() {
	}

	public User create(UserRegistrationRequest request) {
		User user = new User(request.getLogin(), request.getEmail(), request.getPassword());
		for (Role role : request.getRoles()) {
			user.assignRole(role);
		}

		return user;
	}

	public void update(User user, UserUpdateRequest request) {
		user.setEmail(request.getEmail());

		user.relieveFromAllRoles();
		for (Role role : request.getRoles()) {
			user.assignRole(role);
		}
	}
}
