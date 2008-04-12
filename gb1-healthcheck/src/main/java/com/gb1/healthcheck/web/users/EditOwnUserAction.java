package com.gb1.healthcheck.web.users;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserUpdatePropertyProvider;
import com.gb1.struts2.security.AuthenticatedUser;
import com.opensymphony.xwork2.validator.annotations.Validation;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editOwnUser.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "workbench", params = {
				"namespace", "/workbench", "parse", "true", "actionMessageKey",
				"${actionMessageKey}" }) })
@Validation
public class EditOwnUserAction extends EditUserActionSupport {
	private User requester;

	public EditOwnUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		return requester;
	}

	@Override
	protected void updateActiveUserIfNecessary(UserUpdatePropertyProvider updateReq) {
		// the Acegi authenticated user also needs to be updated
		requester.update(updateReq);
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}
