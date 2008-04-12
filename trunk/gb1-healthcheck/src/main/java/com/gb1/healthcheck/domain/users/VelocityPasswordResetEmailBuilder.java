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

@Component("passwordResetEmailBuilder")
public class VelocityPasswordResetEmailBuilder implements PasswordResetEmailBuilder {
	private static final Logger logger = Logger.getLogger(VelocityPasswordResetEmailBuilder.class);

	private JavaMailSender mailSender;
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;

	public VelocityPasswordResetEmailBuilder() {
	}

	public MimeMessage createMessage(User user) {
		MimeMessage mimeMsg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setSubject(subject);
			helper.setFrom(fromAddress);
			helper.setTo(user.getEmail());
			helper.setText(createEmailBody(user), true);
		}
		catch (MessagingException e) {
			logger.error("Error building password reset email", e);
		}

		return mimeMsg;
	}

	private String createEmailBody(User user) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);

		String text = VelocityEngineUtils.mergeTemplateIntoString(engine, templateLocation, model);
		return text;
	}

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Resource
	public void setVelocityEngine(VelocityEngine engine) {
		this.engine = engine;
	}

	@Resource
	public void setGlobalConstants(Map<String, String> constants) {
		fromAddress = constants.get("fromAddress");
		subject = constants.get("passwordReset.subject");
		templateLocation = constants.get("passwordReset.templateLocation");
	}
}
