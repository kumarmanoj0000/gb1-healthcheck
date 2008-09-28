package com.gb1.healthcheck.domain.users;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("passwordResetNotifier")
public class EmailPasswordResetNotifier implements PasswordResetNotifier {
	@Resource
	protected PasswordResetEmailBuilder emailBuilder;

	@Resource
	protected JavaMailSender emailSender;

	public EmailPasswordResetNotifier() {
	}

	public void notifyPasswordReset(User user) {
		MimeMessage msg = emailBuilder.createMessage(user);
		emailSender.send(msg);
	}
}
