package com.gb1.healthcheck.domain.users;

import javax.mail.internet.MimeMessage;

public interface PasswordResetEmailBuilder {
	MimeMessage createMessage(User user);
}
