package com.gb1.healthcheck.domain.users;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * A lost password reminder that sends the user's password to its email address.
 * 
 * @author Guillaume Bilodeau
 */
@Component("lostPasswordReminder")
public class EmailLostPasswordReminder implements LostPasswordReminder {
	private LostPasswordEmailBuilder lostPasswordEmailBuilder;
	private JavaMailSender mailSender;

	public EmailLostPasswordReminder() {
	}

	/**
	 * Sends a reminder to the given user containing his lost password.
	 * 
	 * @param user The user who lost his password
	 */
	public void remindLostPassword(User user) {
		MimeMessage email = lostPasswordEmailBuilder.createReminderMessage(user);
		mailSender.send(email);
	}

	@Resource
	public void setLostPasswordEmailBuilder(LostPasswordEmailBuilder lostPasswordEmailBuilder) {
		this.lostPasswordEmailBuilder = lostPasswordEmailBuilder;
	}

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
