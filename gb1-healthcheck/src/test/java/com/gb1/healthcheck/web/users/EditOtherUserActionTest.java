package com.gb1.healthcheck.web.users;

import java.util.HashMap;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class EditOtherUserActionTest extends TestCase {
	public void testInput() throws Exception {
		User user = Users.lg();

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.getUser(user.getId())).andReturn(user);
		EasyMock.replay(userSvc);

		EditOtherUserAction action = new EditOtherUserAction();
		action.setSession(new HashMap<String, Object>());
		action.setUserService(userSvc);
		action.setUserId(user.getId());

		assertEquals(Action.INPUT, action.input());
		assertEquals(user.getId(), action.getModel().getUserId());
	}
}
