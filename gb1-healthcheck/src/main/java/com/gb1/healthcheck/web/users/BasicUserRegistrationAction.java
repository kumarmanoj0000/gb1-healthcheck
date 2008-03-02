package com.gb1.healthcheck.web.users;

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

/**
 * TODO Rename to UserRegistrationAction
 */
@Validation
public class BasicUserRegistrationAction extends ActionSupport {
	private UserService userService;
	private BasicUserRegistrationRequest userRegRequest = new BasicUserRegistrationRequest(
			Role.STANDARD);

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.login", message = "Login is required."),
			@RequiredStringValidator(fieldName = "model.email", message = "Valid email is required."),
			@RequiredStringValidator(fieldName = "model.password1", message = "Password is required."),
			@RequiredStringValidator(fieldName = "model.password2", message = "Confirmation password is required.") }, emails = { @EmailValidator(fieldName = "model.email", message = "Valid email is required") }, expressions = { @ExpressionValidator(expression = "model.password1.equals(model.password2)", message = "Both passwords must match.") })
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

	@VisitorFieldValidator(message = "")
	public BasicUserRegistrationRequest getModel() {
		return userRegRequest;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
