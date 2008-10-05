package com.gb1.healthcheck.web.users;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.domain.users.User;
import com.opensymphony.xwork2.validator.annotations.Validation;

@Namespace("/admin/users")
@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editOtherUser.jsp"),
		@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace",
				"/admin/users", "parse", "true", "actionMessages", "${actionMessages}",
				"refreshList", "true" }) })
@Validation
public class EditOtherUserAction extends EditUserActionSupport {
	private Long userId;

	public EditOtherUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		return userService.getUser(userId);
	}

	@Override
	protected void updateActiveUserIfNecessary(User user) {
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
