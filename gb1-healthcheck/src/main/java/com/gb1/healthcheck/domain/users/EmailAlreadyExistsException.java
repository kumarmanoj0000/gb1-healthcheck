package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the given email address is already owned by an existing user.
 * 
 * @author Guillaume Bilodeau
 */
public class EmailAlreadyExistsException extends UserException {
	private String email;

	public EmailAlreadyExistsException(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
