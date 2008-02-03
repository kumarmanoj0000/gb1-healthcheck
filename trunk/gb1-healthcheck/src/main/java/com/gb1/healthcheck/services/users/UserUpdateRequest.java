package com.gb1.healthcheck.services.users;

import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserUpdatePropertyProvider;

public interface UserUpdateRequest extends UserUpdatePropertyProvider {
	String getEmail();

	Set<Role> getRoles();
}
