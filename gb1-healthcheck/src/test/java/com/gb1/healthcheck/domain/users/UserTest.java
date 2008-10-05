package com.gb1.healthcheck.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.core.Token;

public class UserTest {
	private static final Token ACTIVATION_TOKEN = new Token("123");
	private static final Token WRONG_ACTIVATION_TOKEN = new Token("456");

	@Test
	public void testCreate() {
		User u = new User();
		assertTrue(u.isWaitingForActivationRequest());
	}

	@Test
	public void testRequestActivation() {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));
		assertTrue(u.isPendingActivation());
	}

	@Test
	public void testActivateOk() throws UserActivationException {
		User u = new User();
		u.activationRequested(new UserActivationRequest(u, ACTIVATION_TOKEN));
		u.activate(ACTIVATION_TOKEN);
		assertTrue(u.isActive());
	}

	@Test
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

	@Test
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

	@Test
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

	@Test
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

	@Test
	public void testChangePasswordOk() throws InvalidPasswordException {
		final String oldPassword = "oldPass";
		final String newPassword = "newPass";

		User u = new User();
		u.setPassword(oldPassword);
		u.changePassword(oldPassword, newPassword);
	}

	@Test
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

	@Test
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

	@Test
	public void testResetPassword() {
		String newPwd = "12345678";
		int length = 8;

		PasswordGenerator pwdGenerator = EasyMock.createMock(PasswordGenerator.class);
		EasyMock.expect(pwdGenerator.generatePassword(length)).andReturn(newPwd);
		EasyMock.replay(pwdGenerator);

		Map<String, String> constants = new HashMap<String, String>();
		constants.put("user.generatedPasswordLength", "8");

		User u = Users.gb();
		u.resetPassword(newPwd);
		assertEquals(newPwd, u.getPassword());
	}
}
