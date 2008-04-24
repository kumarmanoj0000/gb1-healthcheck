package com.gb1.healthcheck.web.users;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.validator.annotations.Validation;

@Namespace("/admin/users")
@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editOtherUser.jsp"),
		@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace",
				"/admin/users", "parse", "true", "actionMessages", "${actionMessages}" }) })
@Validation
public class EditOtherUserAction extends EditUserActionSupport {
	private Long userId;

	public EditOtherUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		User user = getUserService().loadUser(userId);
		return user;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
