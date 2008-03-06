package com.gb1.healthcheck.web.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("lostPasswordAction")
@Scope("prototype")
public class LostPasswordAction extends ActionSupport {
	private String email;
	private UserService userService;

	@Override
	public String input() {
		return Action.INPUT;
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

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
