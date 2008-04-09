package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/changePassword.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "workbench", params = {
				"namespace", "/workbench", "parse", "true", "actionMessageKey",
				"${actionMessageKey}" }) })
@Validation
public class ChangePasswordAction extends ActionSupport {
	private UserService userService;
	private String currentPassword;
	private String newPassword1;
	private String newPassword2;

	public ChangePasswordAction() {
	}

	@Override
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "currentPassword", message = "", key = "users.changePassword.currentPassword.invalid"),
			@RequiredStringValidator(fieldName = "newPassword1", message = "", key = "users.changePassword.newPassword.invalid"),
			@RequiredStringValidator(fieldName = "newPassword2", message = "", key = "users.changePassword.newPassword.invalid") }, expressions = { @ExpressionValidator(expression = "newPassword1.equals(newPassword2)", message = "", key = "users.changePassword.mismatch") })
	public String execute() {
		String result = Action.INPUT;

		try {
			userService.changeUserPassword(getUser().getId(), currentPassword, newPassword1);
			result = Action.SUCCESS;
		}
		catch (InvalidPasswordException e) {
			addActionError(getText("users.changePassword.currentPassword.invalid"));
		}

		return result;
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public User getUser() {
		return getRequester();
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPwd) {
		this.currentPassword = currentPwd;
	}

	public String getNewPassword1() {
		return newPassword1;
	}

	public void setNewPassword1(String newPwd) {
		this.newPassword1 = newPwd;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPwd) {
		this.newPassword2 = newPwd;
	}

	protected User getRequester() {
		return HttpRequestUtils.getUser();
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
