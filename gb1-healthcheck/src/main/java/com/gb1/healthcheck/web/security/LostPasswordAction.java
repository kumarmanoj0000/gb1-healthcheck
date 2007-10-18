package com.gb1.healthcheck.web.security;

import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LostPasswordAction extends ActionSupport {
	private String email;
	private UserService userService;

	public String input() {
		return Action.SUCCESS;
	}

	public String sendLostPassword() {
		String result;

		try {
			userService.sendLostPassword(email);
			result = Action.SUCCESS;
		}
		catch (UnknownUserException e) {
			addFieldError("email", getText("lostPassword.email.unknown"));
			result = Action.INPUT;
		}

		return result;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
