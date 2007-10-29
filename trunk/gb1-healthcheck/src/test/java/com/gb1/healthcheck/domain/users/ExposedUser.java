package com.gb1.healthcheck.domain.users;

import com.gb1.commons.tokens.Token;

/**
 * An exposed user allows complete access to its internal state. This class is a
 * convenience for unit tests and is meant to be used only in the context of
 * such tests.
 * 
 * @author Guillaume Bilodeau
 */
public class ExposedUser extends User {
	public ExposedUser() {
	}

	public ExposedUser(UserRegistrationRequest request) {
		super(request);
	}

	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Override
	public void setLogin(String login) {
		super.setLogin(login);
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public Token getActivationToken() {
		return super.getActivationToken();
	}

	@Override
	public void activationRequested(UserActivationRequest request) {
		super.activationRequested(request);
	}
}
