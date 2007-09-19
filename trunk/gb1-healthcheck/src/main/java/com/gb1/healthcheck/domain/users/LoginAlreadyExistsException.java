package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the given login is already owned by an existing user.
 * 
 * @author Guillaume Bilodeau
 */
public class LoginAlreadyExistsException extends UserException {
	private String login;

	public LoginAlreadyExistsException(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
