package com.gb1.healthcheck.domain.users;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailPasswordResetNotifierTest extends TestCase {
	public void testNotifyPasswordReset() {
		ExposedUser u = new ExposedUser();
		u.setLogin("u1");
		u.setEmail("user@gb.com");
		u.setPassword("1");

		PasswordResetEmailBuilder builder = EasyMock.createMock(PasswordResetEmailBuilder.class);
		EasyMock.expect(builder.createMessage(EasyMock.isA(User.class))).andReturn(
				new MimeMessage((Session) null));
		EasyMock.replay(builder);

		JavaMailSender mailSender = EasyMock.createMock(JavaMailSender.class);
		mailSender.send(EasyMock.isA(MimeMessage.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mailSender);

		EmailPasswordResetNotifier notifier = new EmailPasswordResetNotifier();
		notifier.setEmailBuilder(builder);
		notifier.setMailSender(mailSender);
		notifier.notifyPasswordReset(u);

		// make sure an email was sent
		EasyMock.verify(mailSender);
	}
}
