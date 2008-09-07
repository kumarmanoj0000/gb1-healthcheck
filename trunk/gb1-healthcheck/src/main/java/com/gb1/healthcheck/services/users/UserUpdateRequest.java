package com.gb1.healthcheck.services.users;

import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;

public interface UserUpdateRequest {
	Long getUserId();

	String getEmail();

	Set<Role> getRoles();
}
