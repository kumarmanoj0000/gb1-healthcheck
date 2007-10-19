package com.gb1.healthcheck.domain.users;

public class InvalidPasswordException extends UserException {
	private String password;

	public InvalidPasswordException(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
