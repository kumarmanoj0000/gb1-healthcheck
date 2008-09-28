package com.gb1.healthcheck.web.users;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
			Set<Long> idsToDelete = new HashSet<Long>();
			idsToDelete.addAll(Arrays.asList(userIds));

			userService.deleteUsers(idsToDelete);
			addActionMessage(getText("users.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}
}
