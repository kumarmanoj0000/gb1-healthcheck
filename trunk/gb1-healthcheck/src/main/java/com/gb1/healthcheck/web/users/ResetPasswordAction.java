package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.healthcheck.services.users.UserService;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/admin/users")
@ParentPackage("default")
@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace", "/admin/users",
		"actionName", "manageUsers", "actionMessages", "${actionMessages}" })
public class ResetPasswordAction extends ActionSupport {
	private UserService userService;
	private Long userId;

	public ResetPasswordAction() {
	}

	@Override
	public String execute() {
		userService.resetUserPassword(userId);
		addActionMessage(getText("users.resetPassword.success"));

		return Action.SUCCESS;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Resource
	public void setUserService(UserService userSvc) {
		this.userService = userSvc;
	}
}
