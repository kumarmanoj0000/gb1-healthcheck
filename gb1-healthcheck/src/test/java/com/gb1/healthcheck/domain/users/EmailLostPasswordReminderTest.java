package com.gb1.healthcheck.domain.users;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailLostPasswordReminderTest {
	@Test
	public void testSendLostPassword() {
		ExposedUser u = new ExposedUser();
		u.setLogin("u1");
		u.setEmail("user@gb.com");

		LostPasswordEmailBuilder builder = EasyMock.createMock(LostPasswordEmailBuilder.class);
		EasyMock.expect(builder.createReminderMessage(EasyMock.isA(User.class))).andReturn(
				new MimeMessage((Session) null));
		EasyMock.replay(builder);

		JavaMailSender mailSender = EasyMock.createMock(JavaMailSender.class);
		mailSender.send(EasyMock.isA(MimeMessage.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mailSender);

		EmailLostPasswordReminder reminder = new EmailLostPasswordReminder();
		reminder.lostPasswordEmailBuilder = builder;
		reminder.mailSender = mailSender;
		reminder.remindLostPassword(u);

		// make sure an email was sent
		EasyMock.verify(mailSender);
	}
}
