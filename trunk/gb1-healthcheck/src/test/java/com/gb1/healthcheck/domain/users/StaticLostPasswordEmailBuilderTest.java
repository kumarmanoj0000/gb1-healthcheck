package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.springframework.mail.SimpleMailMessage;

public class StaticLostPasswordEmailBuilderTest extends TestCase {
	public void testCreateReminderMessageOk() {
		final String password = "123";
		User u = new User();
		u.setEmail("user@gb.com");
		u.setPassword(password);

		StaticLostPasswordEmailBuilder builder = new StaticLostPasswordEmailBuilder();
		SimpleMailMessage mail = builder.createReminderMessage(u);

		assertTrue(mail.getText().contains(password));
	}
}
