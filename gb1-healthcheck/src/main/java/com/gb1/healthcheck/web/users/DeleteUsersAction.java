package com.gb1.healthcheck.web.users;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/admin/users")
@ParentPackage("default")
@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace", "/admin/users",
		"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" })
public class DeleteUsersAction extends ActionSupport {
	@Resource
	protected UserService userService;

	private Long[] userIds;

	public DeleteUsersAction() {
	}

	@Override
	public String execute() {
		if (userIds != null) {
			userService.deleteUsers(Arrays.asList(userIds));
			addActionMessage(getText("users.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}
}
