package com.gb1.healthcheck.services.users;

import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;

public interface UserRegistrationRequest {
	String getLogin();

	String getEmail();

	String getPassword();

	Set<Role> getRoles();
}
