package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import com.gb1.commons.tokens.Token;

public class UserTest extends TestCase {
	private static final Token ACTIVATION_TOKEN = new Token("123");
	private static final Token WRONG_ACTIVATION_TOKEN = new Token("456");

	public void testCreate() {
		User u = new User();
		assertTrue(u.isWaitingForActivationRequest());
	}

	public void testRequestActivation() {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));
		assertTrue(u.isPendingActivation());
	}

	public void testActivateOk() throws UserActivationException {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));
		u.activate(ACTIVATION_TOKEN);
		assertTrue(u.isActive());
	}

	public void testActivateAlreadyActive() throws UserActivationException {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));
		u.activate(ACTIVATION_TOKEN);

		// user is now active; try to activate again

		try {
			u.activate(ACTIVATION_TOKEN);
			fail("User was already active");
		}
		catch (UserAlreadyActiveException e) {
			// ok: now make sure user is still active
			assertTrue(u.isActive());
		}
	}

	public void testActivateActivationNotYetRequested() throws UserActivationException {
		User u = new User();

		try {
			u.activate(ACTIVATION_TOKEN);
			fail("Activation had not yet been requested");
		}
		catch (UserActivationNotRequestedException e) {
			// ok: now make sure is still waiting for an activation request
			assertTrue(u.isWaitingForActivationRequest());
		}
	}

	public void testActivateNullToken() throws UserActivationException {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));

		try {
			u.activate(null);
			fail("Submitted token was null");
		}
		catch (InvalidTokenException e) {
			// ok: now make sure user is still pending activation
			assertTrue(u.isPendingActivation());
		}
	}

	public void testActivateInvalidToken() throws UserActivationException {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));

		try {
			u.activate(WRONG_ACTIVATION_TOKEN);
			fail("Submitted token was invalid");
		}
		catch (InvalidTokenException e) {
			// ok: now make sure user is still pending activation
			assertTrue(u.isPendingActivation());
		}
	}

	public void testChangePasswordOk() throws InvalidPasswordException {
		final String oldPassword = "oldPass";
		final String newPassword = "newPass";

		User u = new User();
		u.setPassword(oldPassword);
		u.changePassword(oldPassword, newPassword);
	}

	public void testChangePasswordError() {
		User u = new User();
		u.setPassword("oldPass");

		try {
			u.changePassword("wrongPass", "newPass");
			fail("Old password was invalid");
		}
		catch (InvalidPasswordException e) {
			// ok
		}
	}

	public void testAssignRoles() {
		User u = new User();
		assertTrue(!u.hasRole(Role.STANDARD));

		u.assignRole(Role.STANDARD);
		assertTrue(u.hasRole(Role.STANDARD));

		u.relieveFromRole(Role.ADMINISTRATOR);
		assertTrue(u.hasRole(Role.STANDARD));

		u.relieveFromRole(Role.STANDARD);
		assertTrue(!u.hasRole(Role.STANDARD));
	}
}
