package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ManageUsersActionTest {
	@Test
	public void testExecute() {
		List<User> allUsers = new ArrayList<User>(Users.all());

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.getAllUsers()).andReturn(Users.all()).once();
		EasyMock.replay(userSvc);

		Map<String, Object> sessionMap = new HashMap<String, Object>();

		ManageUsersAction action = new ManageUsersAction();
		action.setSession(sessionMap);
		action.setUserService(userSvc);

		assertEquals(Action.SUCCESS, action.execute());
		assertEquals(allUsers, action.getUsers());
		assertTrue(sessionMap.containsValue(allUsers));

		assertEquals(Action.SUCCESS, action.execute());
		assertEquals(allUsers, action.getUsers());
		assertTrue(sessionMap.containsValue(allUsers));

		// make sure that loading the user list occurs only once
		EasyMock.verify(userSvc);
	}
}
