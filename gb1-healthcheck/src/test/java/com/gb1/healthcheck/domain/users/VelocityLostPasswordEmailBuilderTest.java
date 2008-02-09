package com.gb1.healthcheck.domain.users;

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;

public class VelocityLostPasswordEmailBuilderTest extends TestCase {
	public void testCreateReminderMessageOk() throws Exception {
		User u = new User();
		u.setLogin("user");
		u.setEmail("user@gb.com");
		u.setPassword("123");

		// this will be more like an integration test since we're not mocking the Velocity engine

		Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine engine = new VelocityEngine(props);

		VelocityLostPasswordEmailBuilder builder = new VelocityLostPasswordEmailBuilder();
		builder.setVelocityEngine(engine);
		builder.setTemplateLocation("com/gb1/healthcheck/domain/users/lostPasswordEmail-test.vm");
		SimpleMailMessage mail = builder.createReminderMessage(u);

		String mailText = mail.getText();
		assertTrue(mailText.contains(u.getLogin()));
		assertTrue(mailText.contains(u.getPassword()));
	}
}
