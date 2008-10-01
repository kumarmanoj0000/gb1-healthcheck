package com.gb1.healthcheck.web.users;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public abstract class EditUserActionSupport extends ActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = EditUserActionSupport.class.getName()
			+ ".model";

	@Resource
	protected UserService userService;

	private Map<String, Object> sessionMap;

	public EditUserActionSupport() {
	}

	@Override
	public String input() throws Exception {
		sessionMap.put(MODEL_SESSION_KEY, getUserToEdit());
		return Action.INPUT;
	}

	protected abstract User getUserToEdit();

	@Override
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") }, emails = { @EmailValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") })
	public String execute() {
		String result = Action.INPUT;

		try {
			User userToUpdate = getModel();
			userService.updateUser(userToUpdate);
			updateActiveUserIfNecessary(userToUpdate);

			sessionMap.remove(MODEL_SESSION_KEY);
			addActionMessage(getText("users.edit.success"));
			result = Action.SUCCESS;
		}
		catch (EmailAlreadyExistsException e) {
			addFieldError("model.email", getText("users.edit.email.taken"));
		}
		catch (UserException e) {
			addActionError(getText("users.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	protected abstract void updateActiveUserIfNecessary(User user);

	public String cancel() {
		return Action.SUCCESS;
	}

	public User getModel() {
		return (User) sessionMap.get(MODEL_SESSION_KEY);
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}
