package com.gb1.healthcheck.web.users;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(type = ServletActionRedirectResult.class, value = "listUsers", params = { "namespace",
		"/users", "parse", "true", "actionMessageKey", "${actionMessageKey}", "refreshList", "true" })
public class DeleteUsersAction extends ActionSupport {
	private UserService userService;
	private Long[] userIds;
	private String actionMessageKey;

	public DeleteUsersAction() {
	}

	@Override
	public String execute() {
		if (userIds != null) {
			Set<Long> idsToDelete = new HashSet<Long>();
			idsToDelete.addAll(Arrays.asList(userIds));

			userService.deleteUsers(idsToDelete);
			actionMessageKey = "users.delete.success";
		}

		return Action.SUCCESS;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
