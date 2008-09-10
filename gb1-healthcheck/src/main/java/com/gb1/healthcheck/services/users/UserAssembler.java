package com.gb1.healthcheck.services.users;

import com.gb1.healthcheck.domain.users.User;

public interface UserAssembler {
	User createUser(UserRegistrationRequest request);

	void updateMeal(User user, UserUpdateRequest request);
}
