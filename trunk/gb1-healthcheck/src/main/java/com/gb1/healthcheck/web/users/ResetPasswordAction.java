package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

@Namespace("/admin/users")
@ParentPackage("default")
@Result(type = ServletActionRedirectResult.class, value = "manageUsers", params = { "namespace",
		"/admin/users", "actionName", "manageUsers", "actionMessageKey", "${actionMessageKey}" })
public class ResetPasswordAction {
	private UserService userService;
	private Long userId;
	private String actionMessageKey;

	public ResetPasswordAction() {
	}

	public String execute() {
		userService.resetUserPassword(userId);
		actionMessageKey = "users.resetPassword.success";

		return Action.SUCCESS;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}

	@Resource
	public void setUserService(UserService userSvc) {
		this.userService = userSvc;
	}
}
