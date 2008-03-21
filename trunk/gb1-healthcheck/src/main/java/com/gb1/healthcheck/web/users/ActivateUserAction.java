package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("userActivationAction")
@Scope("prototype")
@Namespace("/public/register")
@ParentPackage("default")
@Results( {
		@Result(name = "input", type = ServletDispatcherResult.class, value = "/views/public/security/register/activateUser.jsp"),
		@Result(type = ServletDispatcherResult.class, value = "/views/public/security/register/activateUser-success.jsp") })
public class ActivateUserAction extends ActionSupport {
	private UserService userService;
	private String email;
	private String token;

	public ActivateUserAction() {
	}

	@Override
	public String execute() {
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
