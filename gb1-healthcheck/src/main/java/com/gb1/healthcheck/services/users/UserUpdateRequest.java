package com.gb1.healthcheck.services.users;

import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserMutablePropertyProvider;

public interface UserUpdateRequest extends UserMutablePropertyProvider {
	String getEmail();

	Set<Role> getRoles();
}
