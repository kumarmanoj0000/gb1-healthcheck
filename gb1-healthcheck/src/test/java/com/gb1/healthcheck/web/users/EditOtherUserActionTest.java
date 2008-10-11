package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class EditOtherUserActionTest {
	@Test
	public void testInput() throws Exception {
		User user = Users.lg();

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.findUser(user.getId())).andReturn(user);
		EasyMock.replay(userSvc);

		EditOtherUserAction action = new EditOtherUserAction();
		action.setSession(new HashMap<String, Object>());
		action.userService = userSvc;
		action.setUserId(user.getId());

		assertEquals(Action.INPUT, action.input());
		assertEquals(user.getId(), action.getModel().getId());
	}
}
