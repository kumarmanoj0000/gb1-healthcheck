package com.gb1.healthcheck.domain.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.mail.SimpleMailMessage;

/**
 * A builder for user activation request email messages using a hard-coded message format.
 * 
 * @author Guillaume Bilodeau
 */
public class StaticUserActivationRequestEmailBuilder implements UserActivationRequestEmailBuilder {
	private String subject;
	private String fromAddress;
	private String activationFormUrl;

	public StaticUserActivationRequestEmailBuilder() {
	}

	/**
	 * Creates a complete email message requesting the user's activation. The created message is
	 * ready to be sent.
	 * 
	 * @param request The activation request
	 * @return The complete email message
	 */
	public SimpleMailMessage createUserActivationRequestEmail(UserActivationRequest request) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject(subject);
		msg.setFrom(fromAddress);
		msg.setTo(request.getPendingUser().getEmail());
		msg.setText(createEmailBody(request));

		return msg;
	}

	private String createEmailBody(UserActivationRequest request) {
		StringBuffer body = new StringBuffer();

		String httpEncodedEmail;
		try {
			httpEncodedEmail = URLEncoder.encode(request.getPendingUser().getEmail(), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			httpEncodedEmail = "";
		}

		body.append("Please activate your account at the following URL:\n");
		body.append(activationFormUrl + "?principal=" + httpEncodedEmail + "\n");
		body.append("---\n");
		body.append("Email: " + request.getPendingUser().getEmail() + "\n");
		body.append("Activation token: " + request.getActivationToken().getValue() + "\n");

		return body.toString();
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setActivationFormUrl(String activationFormUrl) {
		this.activationFormUrl = activationFormUrl;
	}
}
