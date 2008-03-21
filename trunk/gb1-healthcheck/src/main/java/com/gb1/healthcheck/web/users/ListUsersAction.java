package com.gb1.healthcheck.web.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Result(value = "/views/users/listUsers.jsp")
public class ListUsersAction {
	private UserService userService;
	private List<User> users;

	public ListUsersAction() {
	}

	public String execute() {
		Set<User> allUsers = userService.getAllUsers();
		users = new ArrayList<User>(allUsers);
		Collections.sort(users, new User.ByLoginComparator());

		return Action.SUCCESS;
	}

	public List<User> getUsers() {
		return users;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
