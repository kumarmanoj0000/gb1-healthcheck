package com.gb1.healthcheck.web.users;

public class ExposedUserUpdateRequest extends UserUpdateRequest {
	@Override
	public void setId(Long userId) {
		super.setId(userId);
	}
}
