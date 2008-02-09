package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.springframework.mail.SimpleMailMessage;

public class StaticLostPasswordEmailBuilderTest extends TestCase {
	public void testCreateReminderMessageOk() {
		User u = new User();
		u.setEmail("user@gb.com");
		u.setPassword("123");

		StaticLostPasswordEmailBuilder builder = new StaticLostPasswordEmailBuilder();
		SimpleMailMessage mail = builder.createReminderMessage(u);

		assertTrue(mail.getText().contains(u.getPassword()));
	}
}
