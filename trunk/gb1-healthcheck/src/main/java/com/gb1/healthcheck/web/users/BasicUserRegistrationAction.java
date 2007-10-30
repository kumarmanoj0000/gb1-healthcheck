package com.gb1.healthcheck.web.users;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.LoginAlreadyExistsException;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class BasicUserRegistrationAction extends ActionSupport {
	private UserService userService;
	private BasicUserRegistrationRequest userRegRequest = new BasicUserRegistrationRequest(Role.STANDARD);

	public String register() {
		boolean registered = false;

		try {
			userService.registerUser(userRegRequest);
			registered = true;
		}
		catch (LoginAlreadyExistsException e) {
			addFieldError("model.login", getText("register.login.taken"));
		}
		catch (EmailAlreadyExistsException e) {
			addFieldError("model.email", getText("register.email.taken"));
		}
		catch (UserException e) {
			addActionError(getText("register.error"));
		}

		return (registered ? Action.SUCCESS : Action.INPUT);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BasicUserRegistrationRequest getModel() {
		return userRegRequest;
	}
}
