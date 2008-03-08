package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.LoginAlreadyExistsException;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@Controller("userRegistrationAction")
@Scope("prototype")
@Validation
public class UserRegistrationAction extends ActionSupport {
	private UserService userService;
	private BasicUserRegistrationRequest userRegRequest = new BasicUserRegistrationRequest(
			Role.STANDARD);

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.login", message = "", key = "register.login.invalid"),
			@RequiredStringValidator(fieldName = "model.email", message = "", key = "register.email.invalid"),
			@RequiredStringValidator(fieldName = "model.password1", message = "", key = "register.password.invalid"),
			@RequiredStringValidator(fieldName = "model.password2", message = "", key = "register.password.invalid") }, emails = { @EmailValidator(fieldName = "model.email", message = "", key = "register.email.invalid") }, expressions = { @ExpressionValidator(expression = "model.password1.equals(model.password2)", message = "", key = "register.password.mismatch") })
	public String register() {
		String result = Action.INPUT;

		try {
			userService.registerUser(userRegRequest);
			result = Action.SUCCESS;
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

		return result;
	}

	@VisitorFieldValidator(message = "")
	public BasicUserRegistrationRequest getModel() {
		return userRegRequest;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
