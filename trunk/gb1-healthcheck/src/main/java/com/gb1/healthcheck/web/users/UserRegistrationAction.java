package com.gb1.healthcheck.web.users;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.InvalidEmailException;
import com.gb1.healthcheck.domain.users.InvalidLoginException;
import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.LoginAlreadyExistsException;
import com.gb1.healthcheck.domain.users.PasswordMismatchException;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class UserRegistrationAction extends ActionSupport {
	private UserService userService;
	private UserRegistrationRequest userRegRequest = new UserRegistrationRequest(Role.STANDARD);

	public String input() {
		return Action.SUCCESS;
	}

	public String register() {
		boolean registered = false;

		try {
			// validate UI logic (passwords match)
			userRegRequest.validate();

			userService.registerUser(userRegRequest);
			registered = true;
		}
		catch (PasswordMismatchException e) {
			addFieldError("password1", getText("register.password.mismatch"));
		}
		catch (InvalidLoginException e) {
			addFieldError("login", getText("register.login.invalid"));
		}
		catch (InvalidEmailException e) {
			addFieldError("email", getText("register.email.invalid"));
		}
		catch (InvalidPasswordException e) {
			addFieldError("password1", getText("register.password.invalid"));
		}
		catch (LoginAlreadyExistsException e) {
			addFieldError("login", getText("register.login.taken"));
		}
		catch (EmailAlreadyExistsException e) {
			addFieldError("email", getText("register.email.taken"));
		}
		catch (UserException e) {
			addActionError(getText("register.error"));
		}

		return (registered ? Action.SUCCESS : Action.INPUT);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserRegistrationRequest getModel() {
		return userRegRequest;
	}

	void setModel(UserRegistrationRequest userRegRequest) {
		this.userRegRequest = userRegRequest;
	}
}
