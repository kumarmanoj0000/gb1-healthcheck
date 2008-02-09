package com.gb1.healthcheck.domain.users;

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;

import com.gb1.commons.tokens.Token;

public class VelocityUserActivationRequestEmailBuilderTest extends TestCase {
	public void testCreateReminderMessageOk() throws Exception {
		User u = new User();
		u.setLogin("user");
		u.setEmail("user@gb.com");

		UserActivationRequest actRequest = new UserActivationRequest(u, new Token("token1"));
		u.activationRequested(actRequest);

		// this will be more like an integration test since we're not mocking the Velocity engine

		Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine engine = new VelocityEngine(props);

		VelocityUserActivationRequestEmailBuilder builder = new VelocityUserActivationRequestEmailBuilder();
		builder.setVelocityEngine(engine);
		builder
				.setTemplateLocation("com/gb1/healthcheck/domain/users/userActivationRequestEmail-test.vm");
		builder.setActivationFormUrl("http://localhost:8080/healthcheck/public/register/challenge.go");
		SimpleMailMessage mail = builder.createUserActivationRequestEmail(actRequest);

		String mailText = mail.getText();
		assertTrue(mailText.contains(u.getLogin()));
		assertTrue(mailText.contains(actRequest.getActivationToken().getValue()));
	}
}
