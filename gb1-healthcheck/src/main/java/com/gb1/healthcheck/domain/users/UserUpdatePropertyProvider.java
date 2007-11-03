package com.gb1.healthcheck.domain.users;

import java.util.Set;

public interface UserUpdatePropertyProvider {
	String getEmail();

	Set<Role> getRoles();
}
