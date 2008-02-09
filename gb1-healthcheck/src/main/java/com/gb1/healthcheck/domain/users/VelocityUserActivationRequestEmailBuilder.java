package com.gb1.healthcheck.domain.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * A builder for user activation request email messages using a Velocity template.
 * 
 * @author Guillaume Bilodeau
 */
public class VelocityUserActivationRequestEmailBuilder implements UserActivationRequestEmailBuilder {
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;
	private String activationFormUrl;

	public VelocityUserActivationRequestEmailBuilder() {
	}

	public SimpleMailMessage createUserActivationRequestEmail(UserActivationRequest request) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject(subject);
		msg.setFrom(fromAddress);
		msg.setTo(request.getPendingUser().getEmail());
		msg.setText(createEmailBody(request));

		return msg;
	}

	private String createEmailBody(UserActivationRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("request", request);
		model.put("activationUrl", createEncodedActivationUrl(request));

		String text = VelocityEngineUtils.mergeTemplateIntoString(engine, templateLocation, model);
		return text;
	}

	private String createEncodedActivationUrl(UserActivationRequest request) {
		String httpEncodedEmail;
		try {
			httpEncodedEmail = URLEncoder.encode(request.getPendingUser().getEmail(), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			httpEncodedEmail = "";
		}

		String activationUrl = activationFormUrl + "?principal=" + httpEncodedEmail;

		return activationUrl;
	}

	public void setVelocityEngine(VelocityEngine engine) {
		this.engine = engine;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
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
