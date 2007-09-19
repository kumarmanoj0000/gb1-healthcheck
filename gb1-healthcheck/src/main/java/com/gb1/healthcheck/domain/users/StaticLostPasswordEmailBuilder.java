package com.gb1.healthcheck.domain.users;

import org.springframework.mail.SimpleMailMessage;

/**
 * A builder for lost password email messages using a hard-coded message format.
 * 
 * @author Guillaume Bilodeau
 */
public class StaticLostPasswordEmailBuilder implements LostPasswordEmailBuilder {
	private String fromAddress;
	private String subject;

	public StaticLostPasswordEmailBuilder() {
	}

	/**
	 * Creates a complete reminder email message containing the user's lost password. The created
	 * message is ready to be sent.
	 * 
	 * @param user The user who lost his password
	 * @return The complete email message
	 */
	public SimpleMailMessage createReminderMessage(User user) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject(subject);
		msg.setFrom(fromAddress);
		msg.setTo(user.getEmail());
		msg.setText(createMessageBody(user));

		return msg;
	}

	private String createMessageBody(User user) {
		StringBuffer body = new StringBuffer();
		body.append("You have requested your password by email.\n");
		body.append("Your password is: " + user.getPassword());

		return body.toString();
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
