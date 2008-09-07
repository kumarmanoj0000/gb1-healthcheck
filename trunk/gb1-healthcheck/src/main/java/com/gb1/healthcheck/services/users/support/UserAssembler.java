package com.gb1.healthcheck.services.users.support;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserRegistrationRequest;
import com.gb1.healthcheck.services.users.UserUpdateRequest;

public interface UserAssembler {
	User create(UserRegistrationRequest request);

	void update(User user, UserUpdateRequest request);
}
