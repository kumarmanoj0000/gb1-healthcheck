package com.gb1.healthcheck.domain.users;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.gb1.commons.tokens.Token;
import com.gb1.commons.tokens.TokenFactory;

/**
 * A user activation requester that sends an email to the given user requesting his own activation.
 * Beforehand the requester will obtain an activation token that will be sent to the user in order
 * to confirm his activation.
 * 
 * @author Guillaume Bilodeau
 */
@Component("userActivationRequester")
public class EmailUserActivationRequester implements UserActivationRequester {
	private TokenFactory tokenFactory;
	private UserActivationRequestEmailBuilder emailBuilder;
	private JavaMailSender emailSender;

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

		MimeMessage email = emailBuilder.createUserActivationRequestEmail(request);
		emailSender.send(email);

		return request;
	}

	// external dependencies

	@Resource
	public void setTokenFactory(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	@Resource
	public void setEmailBuilder(UserActivationRequestEmailBuilder emailBuilder) {
		this.emailBuilder = emailBuilder;
	}

	@Resource
	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
}
