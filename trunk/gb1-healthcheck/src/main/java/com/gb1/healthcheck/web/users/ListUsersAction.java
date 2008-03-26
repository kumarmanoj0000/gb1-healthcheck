package com.gb1.healthcheck.web.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/users/listUsers.jsp")
public class ListUsersAction extends ActionSupport {
	private static Map<String, Comparator<User>> comparators = new HashMap<String, Comparator<User>>();

	private UserService userService;
	private List<User> users;
	private String sortBy;
	private boolean sortAscending;

	static {
		comparators.put("login", new User.ByLoginComparator());
		comparators.put("email", new User.ByEmailComparator());
	}

	public ListUsersAction() {
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public boolean getSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	public String execute() {
		users = sortUsersBy(userService.getAllUsers(), sortBy, true);
		return Action.SUCCESS;
	}

	public String sort() {
		users = sortUsersBy(userService.getAllUsers(), sortBy, sortAscending);
		return Action.SUCCESS;
	}

	public List<User> getUsers() {
		return users;
	}

	private List<User> sortUsersBy(Set<User> allUsers, String sortProperty, boolean ascending) {
		List<User> sortedUsers = new ArrayList<User>(allUsers);
		Collections.sort(sortedUsers, getComparator(sortProperty, ascending));

		return sortedUsers;
	}

	private Comparator<User> getComparator(String sortProperty, boolean ascending) {
		Comparator<User> comp = comparators.get(sortProperty);

		if (comp == null) {
			comp = new User.ByLoginComparator();
		}

		if (!ascending) {
			comp = Collections.reverseOrder(comp);
		}

		return comp;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
