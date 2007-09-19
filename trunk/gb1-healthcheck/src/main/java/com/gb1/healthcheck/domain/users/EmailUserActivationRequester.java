package com.gb1.healthcheck.domain.users;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.gb1.commons.tokens.Token;
import com.gb1.commons.tokens.TokenFactory;

/**
 * A user activation requester that sends an email to the given user requesting his own activation.
 * Beforehand the requester will obtain an activation token that will be sent to the user in order
 * to confirm his activation.
 * 
 * @author Guillaume Bilodeau
 */
public class EmailUserActivationRequester implements UserActivationRequester {
	private TokenFactory tokenFactory;
	private UserActivationRequestEmailBuilder emailBuilder;
	private MailSender emailSender;

	public EmailUserActivationRequester() {
	}

	/**
	 * Requests activation for the specified user.
	 * 
	 * @param user The user to be activated
	 * @return The user activation request
	 */
	public UserActivationRequest requestUserActivation(User user) {
		Token activationToken = tokenFactory.newToken();

		UserActivationRequest request = new UserActivationRequest(user, activationToken);
		user.activationRequested(request);

		SimpleMailMessage email = emailBuilder.createUserActivationRequestEmail(request);
		emailSender.send(email);

		return request;
	}

	// external dependencies

	public void setTokenFactory(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	public void setEmailBuilder(UserActivationRequestEmailBuilder emailBuilder) {
		this.emailBuilder = emailBuilder;
	}

	public void setEmailSender(MailSender emailSender) {
		this.emailSender = emailSender;
	}
}
