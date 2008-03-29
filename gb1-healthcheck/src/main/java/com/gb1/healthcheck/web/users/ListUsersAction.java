package com.gb1.healthcheck.web.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/users/listUsers.jsp")
public class ListUsersAction extends ActionSupport implements SessionAware {
	private static final String USER_LIST_SESSION_KEY = ListUsersAction.class.getName()
			+ ".cachedUserList";
	private Map<String, Object> sessionMap;
	private UserService userService;

	public ListUsersAction() {
	}

	public String execute() {
		List<User> userList = getUsers();

		if (userList == null) {
			Set<User> users = userService.getAllUsers();
			userList = new ArrayList<User>(users);
			sessionMap.put(USER_LIST_SESSION_KEY, userList);
		}

		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		List<User> userList = (List<User>) sessionMap.get(USER_LIST_SESSION_KEY);
		return userList;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
