package com.gb1.healthcheck.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

import com.gb1.healthcheck.core.Token;
import com.gb1.healthcheck.core.TokenFactory;

public class EmailUserActivationRequesterTest {
	@Test
	public void testRequestUserActivationOk() {
		Token activationToken = new Token("123");

		ExposedUser user = new ExposedUser();
		user.setLogin("user");
		user.setEmail("user@gb1.com");

		TokenFactory factory = EasyMock.createMock(TokenFactory.class);
		EasyMock.expect(factory.newToken()).andReturn(activationToken);
		EasyMock.replay(factory);

		UserActivationRequestEmailBuilder emailBuilder = EasyMock
				.createMock(UserActivationRequestEmailBuilder.class);
		EasyMock.expect(
				emailBuilder.createUserActivationRequestEmail(EasyMock
						.isA(UserActivationRequest.class))).andReturn(
				new MimeMessage((Session) null));
		EasyMock.replay(emailBuilder);

		JavaMailSender emailSender = EasyMock.createMock(JavaMailSender.class);
		emailSender.send(EasyMock.isA(MimeMessage.class));
		EasyMock.replay(emailSender);

		EmailUserActivationRequester activator = new EmailUserActivationRequester();
		activator.tokenFactory = factory;
		activator.emailBuilder = emailBuilder;
		activator.emailSender = emailSender;

		UserActivationRequest request = activator.requestUserActivation(user);

		assertEquals(request.getPendingUser().getEmail(), user.getEmail());
		assertNotNull(user.getActivationToken());
		assertTrue(!user.isActive());

		// makes sure an email has been sent
		EasyMock.verify(emailSender);
	}
}
