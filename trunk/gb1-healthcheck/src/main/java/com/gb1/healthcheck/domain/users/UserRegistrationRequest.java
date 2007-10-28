package com.gb1.healthcheck.domain.users;

import java.util.Set;

public interface UserRegistrationRequest {
	String getEmail();

	String getLogin();

	String getPassword();

	Set<Role> getRoles();
}
