package com.gb1.healthcheck.web.users;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.web.WebConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/admin/users")
@ParentPackage("default")
@Result(value = "/views/users/manageUsers.jsp")
public class ManageUsersAction extends ActionSupport implements SessionAware {
	public static final String USER_LIST_SESSION_KEY = ManageUsersAction.class.getName()
			+ ".cachedUserList";

	@Resource
	protected UserService userService;

	private Map<String, Object> sessionMap;
	private boolean refreshList;

	public ManageUsersAction() {
	}

	@Override
	public String execute() {
		List<User> userList = getUsers();

		if (userList == null || refreshList) {
			userList = userService.getAllUsers();
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

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}
