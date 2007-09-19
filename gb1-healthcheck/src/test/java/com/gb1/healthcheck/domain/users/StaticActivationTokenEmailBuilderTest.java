package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.springframework.mail.SimpleMailMessage;

import com.gb1.commons.tokens.Token;

public class StaticActivationTokenEmailBuilderTest extends TestCase {
	public void testCreateActivationTokenEmailOk() {
		User u = new User();
		u.setEmail("user@gb.com");
		Token token = new Token("123");
		UserActivationRequest request = new UserActivationRequest(u, token);

		StaticUserActivationRequestEmailBuilder builder = new StaticUserActivationRequestEmailBuilder();
		SimpleMailMessage mail = builder.createUserActivationRequestEmail(request);

		assertTrue(mail.getText().contains(u.getEmail()));
		assertTrue(mail.getText().contains(token.getValue()));
	}
}
