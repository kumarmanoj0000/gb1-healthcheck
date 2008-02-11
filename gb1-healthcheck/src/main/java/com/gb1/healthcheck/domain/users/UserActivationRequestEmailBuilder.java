package com.gb1.healthcheck.domain.users;

import javax.mail.internet.MimeMessage;

/**
 * A builder for user activation request email messages.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserActivationRequestEmailBuilder {
	/**
	 * Creates a complete email message requesting the user's activation. The created message should
	 * be ready to be sent.
	 * 
	 * @param request The activation request
	 * @return The complete email message
	 */
	MimeMessage createUserActivationRequestEmail(UserActivationRequest request);
}
