package com.gb1.healthcheck.web.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class DeleteUsersActionTest extends TestCase {
	public void testDeleteUsers() {
		List<User> users = new ArrayList<User>();
		users.add(Users.gb());
		users.add(Users.lg());

		Set<Long> userIds = new HashSet<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.deleteUsers(userIds);
		EasyMock.expectLastCall();
		EasyMock.replay(userSvc);

		DeleteUsersAction action = new DeleteUsersAction();
		action.setUserService(userSvc);
		action.setUserIds(userIds.toArray(new Long[0]));

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}
}
