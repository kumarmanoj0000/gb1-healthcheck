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

	private Map<String, Object> sessionMap;
	private UserService userService;

	public EditUserActionSupport() {
	}

	@Override
	public String input() throws Exception {
		BasicUserUpdateRequest model = new BasicUserUpdateRequest(getUserToEdit());
		sessionMap.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	protected abstract User getUserToEdit();

	@Override
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") }, emails = { @EmailValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") })
	public String execute() {
		String result = Action.INPUT;

		try {
			BasicUserUpdateRequest updateReq = getModel();
			userService.updateUser(updateReq);

			updateActiveUserIfNecessary(updateReq);

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

	protected void updateActiveUserIfNecessary(BasicUserUpdateRequest updateReq) {
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public BasicUserUpdateRequest getModel() {
		BasicUserUpdateRequest model = (BasicUserUpdateRequest) sessionMap.get(MODEL_SESSION_KEY);
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	protected UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userSvc) {
		this.userService = userSvc;
	}
}
