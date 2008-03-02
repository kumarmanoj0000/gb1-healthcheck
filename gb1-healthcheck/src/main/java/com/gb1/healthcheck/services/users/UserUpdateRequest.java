package com.gb1.healthcheck.services.users;

import com.gb1.healthcheck.domain.users.UserUpdatePropertyProvider;

public interface UserUpdateRequest extends UserUpdatePropertyProvider {
	Long getUserId();
}
