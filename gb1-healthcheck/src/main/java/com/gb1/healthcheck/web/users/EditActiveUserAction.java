package com.gb1.healthcheck.web.users;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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

@Controller("editActiveUserAction")
@Scope("prototype")
@Validation
public class EditActiveUserAction extends ActionSupport implements ServletRequestAware,
		SessionAware {
	protected static final String MODEL_SESSION_KEY = EditActiveUserAction.class.getName()
			+ ".model";

	private HttpServletRequest request;
	private Map<String, Object> sessionMap;
	private UserService userService;
	private String actionMessageKey;

	public EditActiveUserAction() {
	}

	@Override
	public String input() throws Exception {
		BasicUserUpdateRequest model = new BasicUserUpdateRequest(getUser());
		sessionMap.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") }, emails = { @EmailValidator(fieldName = "model.email", message = "", key = "users.edit.email.invalid") })
	public String submit() {
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
		return HttpRequestUtils.getUser(request);
	}

	public BasicUserUpdateRequest getModel() {
		BasicUserUpdateRequest model = (BasicUserUpdateRequest) sessionMap.get(MODEL_SESSION_KEY);
		return model;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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
