package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("userActivationAction")
@Scope("prototype")
public class UserActivationAction extends ActionSupport {
	private UserService userService;
	private String email;
	private String token;

	public String activate() {
		String result = Action.INPUT;

		try {
			userService.activateUser(email, new Token(token));
			result = Action.SUCCESS;
		}
		catch (UserException e) {
			addFieldError("credentials", getText("register.activate.activationToken.invalid"));
		}

		return result;
	}

	public void setPrincipal(String email) {
		this.email = email;
	}

	public String getPrincipal() {
		return email;
	}

	public void setCredentials(String token) {
		this.token = token;
	}

	public String getCredentials() {
		return token;
	}

	@Resource
	public void setUserService(UserService userSvc) {
		this.userService = userSvc;
	}
}
