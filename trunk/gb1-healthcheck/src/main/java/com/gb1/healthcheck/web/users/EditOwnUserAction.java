package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.support.UserAssembler;
import com.gb1.struts2.dispatcher.FlashResult;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.validator.annotations.Validation;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/users/editOwnUser.jsp"),
		@Result(type = FlashResult.class, value = "manageUsers", params = { "namespace",
				"/admin/users", "parse", "true", "actionMessages", "${actionMessages}" }) })
@Validation
public class EditOwnUserAction extends EditUserActionSupport {
	private UserAssembler userAssembler;
	private User requester;

	public EditOwnUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		return requester;
	}

	@Override
	protected void updateActiveUserIfNecessary(BasicUserUpdateRequest updateReq) {
		// the session authenticated user also needs to be updated
		userAssembler.update(requester, updateReq);
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}

	@Resource
	public void setUserAssembler(UserAssembler userAssembler) {
		this.userAssembler = userAssembler;
	}
}
