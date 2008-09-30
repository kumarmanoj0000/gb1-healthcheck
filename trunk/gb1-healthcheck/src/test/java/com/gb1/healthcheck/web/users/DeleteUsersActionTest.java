package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class DeleteUsersActionTest {
	@Test
	public void testDeleteUsers() {
		List<User> users = new ArrayList<User>();
		users.add(Users.gb());
		users.add(Users.lg());

		List<Long> userIds = new ArrayList<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.deleteUsers(userIds);
		EasyMock.expectLastCall();
		EasyMock.replay(userSvc);

		DeleteUsersAction action = new DeleteUsersAction();
		action.userService = userSvc;
		action.setUserIds(userIds.toArray(new Long[0]));

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}
}
