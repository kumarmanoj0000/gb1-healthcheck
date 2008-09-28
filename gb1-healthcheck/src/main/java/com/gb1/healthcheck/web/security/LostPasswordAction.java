package com.gb1.healthcheck.web.security;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/public/security")
@ParentPackage("default")
@Results( { @Result(name = "input", value = "/views/public/security/lostPassword.jsp"),
		@Result(value = "/views/public/security/lostPassword-success.jsp") })
public class LostPasswordAction extends ActionSupport {
	@Resource
	protected UserService userService;

	private String email;

	public LostPasswordAction() {
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			userService.sendLostPassword(email);
			result = Action.SUCCESS;
		}
		catch (UnknownUserException e) {
			addFieldError("email", getText("lostPassword.email.unknown"));
		}

		return result;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
