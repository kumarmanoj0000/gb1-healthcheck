package com.gb1.healthcheck.domain.users;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class VelocityLostPasswordEmailBuilder implements LostPasswordEmailBuilder {
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;

	public VelocityLostPasswordEmailBuilder() {
	}

	public SimpleMailMessage createReminderMessage(User user) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject(subject);
		msg.setFrom(fromAddress);
		msg.setTo(user.getEmail());
		msg.setText(createMessageBody(user));

		return msg;
	}

	private String createMessageBody(User user) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		String text = VelocityEngineUtils.mergeTemplateIntoString(engine, templateLocation, model);

		return text;
	}

	public void setVelocityEngine(VelocityEngine engine) {
		this.engine = engine;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
