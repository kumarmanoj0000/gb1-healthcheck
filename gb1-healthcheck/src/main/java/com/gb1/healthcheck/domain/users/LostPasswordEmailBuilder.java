package com.gb1.healthcheck.domain.users;

import org.springframework.mail.SimpleMailMessage;

/**
 * A builder for lost password email messages.
 * 
 * @author Guillaume Bilodeau
 */
public interface LostPasswordEmailBuilder {
	/**
	 * Creates a complete reminder email message containing the user's lost password. The created
	 * message should be ready to be sent.
	 * 
	 * @param user The user who lost his password
	 * @return The complete email message
	 */
	SimpleMailMessage createReminderMessage(User user);
}
