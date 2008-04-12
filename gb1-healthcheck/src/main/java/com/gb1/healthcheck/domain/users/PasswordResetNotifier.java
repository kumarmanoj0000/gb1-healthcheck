package com.gb1.healthcheck.domain.users;

public interface PasswordResetNotifier {
	void notifyPasswordReset(User user);
}
