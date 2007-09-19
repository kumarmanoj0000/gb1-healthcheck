package com.gb1.healthcheck.web.users;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.users.PasswordMismatchException;

public class UserRegistrationRequestTest extends TestCase {
	public void testValidateWithMismatchedPasswords() {
		UserRegistrationRequest userRegRequest = new UserRegistrationRequest();
		userRegRequest.setPassword1("p1");
		userRegRequest.setPassword2("p2");

		try {
			userRegRequest.validate();
			fail("Passwords don't match");
		}
		catch (PasswordMismatchException e) {
			// ok
		}
	}
}
