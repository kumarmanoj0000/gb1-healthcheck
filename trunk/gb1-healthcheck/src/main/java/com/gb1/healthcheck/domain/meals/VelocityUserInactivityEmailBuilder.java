package com.gb1.healthcheck.domain.meals;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.gb1.healthcheck.domain.users.User;

@Component("userInactivityEmailBuilder")
public class VelocityUserInactivityEmailBuilder implements UserInactivityEmailBuilder {
	private static final Logger logger = Logger.getLogger(VelocityUserInactivityEmailBuilder.class);
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private JavaMailSender mailSender;
	private VelocityEngine engine;
	private String templateLocation;
	private String fromAddress;
	private String subject;

	public VelocityUserInactivityEmailBuilder() {
	}

	public MimeMessage createMessage(User user, Meal lastMeal) {
		MimeMessage mimeMsg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setSubject(subject);
			helper.setFrom(fromAddress);
			helper.setTo(user.getEmail());
			helper.setText(createMessageBody(user, lastMeal), true);
		}
		catch (MessagingException e) {
			logger.error("Error creating lost password email", e);
		}

		return mimeMsg;
	}

	private String createMessageBody(User user, Meal lastMeal) {
		String lastMealDate = (lastMeal == null ? "forever" : DateFormatUtils.format(lastMeal
				.getInstant(), DATE_PATTERN));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		model.put("lastMealDate", lastMealDate);
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
	public void setGlobalConstants(Map<String, String> constants) {
		fromAddress = constants.get("fromAddress");
		subject = constants.get("userInactivity.subject");
		templateLocation = constants.get("userInactivity.templateLocation");
	}
}
