package com.gb1.healthcheck.web.users;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.commons.pagination.PaginatedListScroller;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/users/listUsers.jsp")
public class ListUsersAction extends ActionSupport {
	private UserService userService;
	private PaginatedListScroller<User> paginatedUserList;

	public ListUsersAction() {
	}

	public String execute() {
		return Action.SUCCESS;
	}

	public PaginatedListScroller<User> getUsers() {
		return paginatedUserList;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
