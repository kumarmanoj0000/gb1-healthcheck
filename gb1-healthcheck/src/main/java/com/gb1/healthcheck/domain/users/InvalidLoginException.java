package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the given login is invalid. Constraints such as minimal length and accepted
 * characters can cause a login to be rejected.
 * 
 * @author Guillaume Bilodeau
 */
public class InvalidLoginException extends UserException {
	private String login;

	public InvalidLoginException(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
