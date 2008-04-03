package com.gb1.healthcheck.web.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.web.WebConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/users/listUsers.jsp")
public class ListUsersAction extends ActionSupport implements SessionAware {
	public static final String USER_LIST_SESSION_KEY = ListUsersAction.class.getName()
			+ ".cachedUserList";

	private Map<String, Object> sessionMap;
	private boolean refreshList;
	private UserService userService;

	public ListUsersAction() {
	}

	@Override
	public String execute() {
		List<User> userList = getUsers();

		if (userList == null || refreshList) {
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

	public int getUserListPageSize() {
		return WebConstants.DEFAULT_PAGE_SIZE;
	}

	public void setRefreshList(boolean refreshList) {
		this.refreshList = refreshList;
	}

	public void setActionMessageKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
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
