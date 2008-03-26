package com.gb1.healthcheck.web.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ListUsersActionTest extends TestCase {
	public void testExecute() {
		Set<User> allUsers = Users.all();
		List<User> allSortedUsers = new ArrayList<User>(allUsers);
		Collections.sort(allSortedUsers, new User.ByLoginComparator());

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.getAllUsers()).andReturn(allUsers);
		EasyMock.replay(userSvc);

		ListUsersAction action = new ListUsersAction();
		action.setUserService(userSvc);

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(allSortedUsers, action.getUsers()));
	}
}
