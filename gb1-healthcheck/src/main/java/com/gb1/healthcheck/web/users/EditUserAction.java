package com.gb1.healthcheck.web.users;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editUser.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "workbench", params = {
				"namespace", "/workbench", "parse", "true", "actionMessageKey",
				"${actionMessageKey}" }) })
@Validation
public class EditUserAction extends ActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = EditUserAction.class.getName() + ".model";

	private Map<String, Object> sessionMap;
	private UserService userService;

	private Long userId;
	private String actionMessageKey;

	public EditUserAction() {
	}

	@Override
	public String input() throws Exception {
		User userToEdit = userService.loadUser(userId);
		BasicUserUpdateRequest model = new BasicUserUpdateRequest(userToEdit);
		sessionMap.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") }, emails = { @EmailValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			User activeUser = getUser();
			BasicUserUpdateRequest updateReq = getModel();

			// on top of the usual update, the Acegi authenticated user must also be updated
			userService.updateUser(updateReq);
			activeUser.update(updateReq);

			sessionMap.remove(MODEL_SESSION_KEY);
			actionMessageKey = "users.edit.success";
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

	public String cancel() {
		return Action.SUCCESS;
	}

	public User getUser() {
		return HttpRequestUtils.getUser();
	}

	public BasicUserUpdateRequest getModel() {
		BasicUserUpdateRequest model = (BasicUserUpdateRequest) sessionMap.get(MODEL_SESSION_KEY);
		return model;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Resource
	public void setUserService(UserService userSvc) {
		this.userService = userSvc;
	}
}
