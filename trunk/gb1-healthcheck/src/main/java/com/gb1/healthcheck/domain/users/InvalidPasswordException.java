package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the provided password does not match the user's current password.
 * 
 * @author Guillaume Bilodeau
 */
public class InvalidPasswordException extends UserException {
	private String password;

	public InvalidPasswordException(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
