package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.gb1.commons.tokens.Token;
import com.gb1.commons.tokens.TokenFactory;

public class EmailUserActivationRequesterTest extends TestCase {
	public void testRequestUserActivationOk() {
		Token activationToken = new Token("123");

		ExposedUser user = new ExposedUser();
		user.setLogin("user");
		user.setEmail("user@gb1.com");

		SimpleMailMessage email = new SimpleMailMessage();

		TokenFactory factory = EasyMock.createMock(TokenFactory.class);
		EasyMock.expect(factory.newToken()).andReturn(activationToken);
		EasyMock.replay(factory);

		UserActivationRequestEmailBuilder emailBuilder = EasyMock
				.createMock(UserActivationRequestEmailBuilder.class);
		EasyMock.expect(
				emailBuilder.createUserActivationRequestEmail(EasyMock
						.isA(UserActivationRequest.class))).andReturn(email);
		EasyMock.replay(emailBuilder);

		MailSender emailSender = EasyMock.createMock(MailSender.class);
		emailSender.send(email);
		EasyMock.replay(emailSender);

		EmailUserActivationRequester activator = new EmailUserActivationRequester();
		activator.setTokenFactory(factory);
		activator.setEmailBuilder(emailBuilder);
		activator.setEmailSender(emailSender);

		UserActivationRequest request = activator.requestUserActivation(user);

		assertEquals(request.getPendingUser().getEmail(), user.getEmail());
		assertNotNull(user.getActivationToken());
		assertTrue(!user.isActive());

		// makes sure an email has been sent
		EasyMock.verify(emailSender);
	}

	public void testRequestUserActivationAlreadyActive() {
	}
}
