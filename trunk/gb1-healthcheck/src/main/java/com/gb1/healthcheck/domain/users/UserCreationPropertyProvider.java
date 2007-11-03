package com.gb1.healthcheck.domain.users;

import java.util.Set;

public interface UserCreationPropertyProvider {
	String getLogin();

	String getPassword();

	String getEmail();

	Set<Role> getRoles();
}
