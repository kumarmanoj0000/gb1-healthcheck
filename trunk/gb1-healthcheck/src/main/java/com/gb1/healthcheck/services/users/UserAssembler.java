package com.gb1.healthcheck.services.users;

import com.gb1.healthcheck.domain.users.User;

public interface UserAssembler {
	User create(UserRegistrationRequest request);

	void update(User user, UserUpdateRequest request);
}
