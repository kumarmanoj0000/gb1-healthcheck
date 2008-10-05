package com.gb1.healthcheck.web.users;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;
import org.apache.struts2.interceptor.AuthenticatedUser;

import com.gb1.healthcheck.domain.users.User;
import com.opensymphony.xwork2.validator.annotations.Validation;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editOwnUser.jsp"),
		@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace",
				"/admin/users", "parse", "true", "actionMessages", "${actionMessages}",
				"refreshList", "true" }) })
@Validation
public class EditOwnUserAction extends EditUserActionSupport {
	private User requester;

	public EditOwnUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		return new User(requester);
	}

	@Override
	protected void updateActiveUserIfNecessary(User user) {
		// the session authenticated user also needs to be updated
		requester.updateFrom(user);
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}
