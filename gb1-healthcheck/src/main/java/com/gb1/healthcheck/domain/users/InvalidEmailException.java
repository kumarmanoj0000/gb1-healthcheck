package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the given email address is invalid.
 * 
 * @author Guillaume Bilodeau
 */
public class InvalidEmailException extends UserException {
	private String email;

	public InvalidEmailException(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
