package com.gb1.healthcheck.domain.users;

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
 * A builder for lost password reminder email messages using a Velocity template.
 * 
 * @author Guillaume Bilodeau
 */
@Component("lostPasswordEmailBuilder")
public class VelocityLostPasswordEmailBuilder implements LostPasswordEmailBuilder {
	private static final Logger logger = Logger.getLogger(VelocityLostPasswordEmailBuilder.class);

	private JavaMailSender mailSender;
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;

	public VelocityLostPasswordEmailBuilder() {
	}

	public MimeMessage createReminderMessage(User user) {
		MimeMessage mimeMsg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setSubject(subject);
			helper.setFrom(fromAddress);
			helper.setTo(user.getEmail());
			helper.setText(createMessageBody(user), true);
		}
		catch (MessagingException e) {
			logger.error("Error creating lost password email", e);
		}

		return mimeMsg;
	}

	private String createMessageBody(User user) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		String text = VelocityEngineUtils.mergeTemplateIntoString(engine, templateLocation, model);

		return text;
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
	public void setLostPasswordTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	@Resource
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Resource
	public void setLostPasswordSubject(String subject) {
		this.subject = subject;
	}
}
