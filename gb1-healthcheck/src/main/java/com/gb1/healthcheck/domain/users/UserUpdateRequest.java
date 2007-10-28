package com.gb1.healthcheck.domain.users;

import java.util.Set;

public interface UserUpdateRequest {
	String getEmail();

	Set<Role> getRoles();
}
