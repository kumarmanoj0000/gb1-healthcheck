package com.gb1.healthcheck.domain.users;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * A lost password reminder that sends the user's password to its email address.
 * 
 * @author Guillaume Bilodeau
 */
public class EmailLostPasswordReminder implements LostPasswordReminder {
	private LostPasswordEmailBuilder lostPasswordEmailBuilder;
	private MailSender mailSender;

	public EmailLostPasswordReminder() {
	}

	/**
	 * Sends a reminder to the given user containing his lost password.
	 * 
	 * @param user The user who lost his password
	 */
	public void remindLostPassword(User user) {
		SimpleMailMessage email = lostPasswordEmailBuilder.createReminderMessage(user);
		mailSender.send(email);
	}

	public void setLostPasswordEmailBuilder(LostPasswordEmailBuilder lostPasswordEmailBuilder) {
		this.lostPasswordEmailBuilder = lostPasswordEmailBuilder;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
}
