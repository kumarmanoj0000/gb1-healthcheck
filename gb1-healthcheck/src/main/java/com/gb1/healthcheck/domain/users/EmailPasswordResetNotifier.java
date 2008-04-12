package com.gb1.healthcheck.domain.users;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("passwordResetNotifier")
public class EmailPasswordResetNotifier implements PasswordResetNotifier {
	private PasswordResetEmailBuilder emailBuilder;
	private JavaMailSender mailSender;

	public EmailPasswordResetNotifier() {
	}

	public void notifyPasswordReset(User user) {
		MimeMessage msg = emailBuilder.createMessage(user);
		mailSender.send(msg);
	}

	@Resource
	public void setEmailBuilder(PasswordResetEmailBuilder builder) {
		this.emailBuilder = builder;
	}

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
