package com.gb1.healthcheck.domain.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * A builder for user activation request email messages using a Velocity template.
 * 
 * @author Guillaume Bilodeau
 */
@Component("userActivationRequestEmailBuilder")
public class VelocityUserActivationRequestEmailBuilder implements UserActivationRequestEmailBuilder {
	private static final Logger logger = Logger
			.getLogger(VelocityUserActivationRequestEmailBuilder.class);

	private JavaMailSender mailSender;
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;
	private String activationFormUrl;

	public VelocityUserActivationRequestEmailBuilder() {
	}

	public MimeMessage createUserActivationRequestEmail(UserActivationRequest request) {
		MimeMessage mimeMsg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setSubject(subject);
			helper.setFrom(fromAddress);
			helper.setTo(request.getPendingUser().getEmail());
			helper.setText(createEmailBody(request), true);
		}
		catch (MessagingException e) {
			logger.error("Error creating lost password email", e);
		}

		return mimeMsg;
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

	// external dependencies

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Resource
	public void setVelocityEngine(VelocityEngine engine) {
		this.engine = engine;
	}

	@Resource
	public void setActivationTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	@Resource
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Resource
	public void setActivationSubject(String subject) {
		this.subject = subject;
	}

	@Resource
	public void setActivationFormUrl(String activationFormUrl) {
		this.activationFormUrl = activationFormUrl;
	}
}
