package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailLostPasswordSenderTest extends TestCase {
	public void testSendLostPassword() {
		ExposedUser u = new ExposedUser();
		u.setLogin("u1");
		u.setEmail("user@gb.com");

		LostPasswordEmailBuilder builder = EasyMock.createMock(LostPasswordEmailBuilder.class);
		EasyMock.expect(builder.createReminderMessage(EasyMock.isA(User.class))).andReturn(
				new SimpleMailMessage());
		EasyMock.replay(builder);

		MailSender mailSender = EasyMock.createMock(MailSender.class);
		mailSender.send(EasyMock.isA(SimpleMailMessage.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mailSender);

		EmailLostPasswordReminder reminder = new EmailLostPasswordReminder();
		reminder.setLostPasswordEmailBuilder(builder);
		reminder.setMailSender(mailSender);
		reminder.remindLostPassword(u);

		// make sure an email was sent
		EasyMock.verify(mailSender);
	}
}
