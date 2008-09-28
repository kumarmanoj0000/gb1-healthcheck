package com.gb1.healthcheck.services.users;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.User;

@Component("userAssembler")
public class UserAssemblerImpl implements UserAssembler {
	public UserAssemblerImpl() {
	}

	public User createUser(UserRegistrationRequest request) {
		User user = new User(request.getLogin(), request.getPassword());
		user.setEmail(request.getEmail());

		for (Role role : request.getRoles()) {
			user.assignRole(role);
		}

		return user;
	}

	public void updateMeal(User user, UserUpdateRequest request) {
		user.setEmail(request.getEmail());

		user.relieveFromAllRoles();
		for (Role role : request.getRoles()) {
			user.assignRole(role);
		}
	}
}
