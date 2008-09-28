package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserAssemblerImpl;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.services.users.UserUpdateRequest;
import com.opensymphony.xwork2.Action;

public class EditOwnUserActionTest {
	@Test
	public void testInput() throws Exception {
		User user = Users.lg();

		EditOwnUserAction action = new EditOwnUserAction();
		action.setSession(new HashMap<String, Object>());
		action.setRequester(user);

		assertEquals(Action.INPUT, action.input());
		assertEquals(user.getId(), action.getModel().getUserId());
	}

	@Test
	public void testSubmit() throws Exception {
		User user = Users.gb();
		BasicUserUpdateRequest request = new BasicUserUpdateRequest(user);
		request.setEmail("newEmail@yahoo.com");

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(EditUserActionSupport.MODEL_SESSION_KEY, request);

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.updateUser(request)).andReturn(user);
		EasyMock.replay(userSvc);

		EditOwnUserAction action = new EditOwnUserAction();
		action.userAssembler = new UserAssemblerImpl();
		action.userService = userSvc;
		action.setRequester(user);
		action.setSession(sessionMap);

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(sessionMap.isEmpty());

		// make sure the update was sent to the service layer
		EasyMock.verify(userSvc);

		// make sure the session's authenticated user was updated too
		assertEquals(request.getEmail(), user.getEmail());
	}

	@Test
	public void testSubmitWithErrors() throws Exception {
		User user = Users.gb();
		UserUpdateRequest request = new BasicUserUpdateRequest(user);

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(EditUserActionSupport.MODEL_SESSION_KEY, request);

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.updateUser(request)).andThrow(
				new EmailAlreadyExistsException(user.getEmail()));
		EasyMock.replay(userSvc);

		EditOwnUserAction action = new EditOwnUserAction();
		action.setRequester(user);
		action.userService = userSvc;
		action.setSession(sessionMap);

		assertEquals(Action.INPUT, action.execute());
		assertNotNull(action.getFieldErrors().get("model.email"));
		assertTrue(sessionMap.containsKey(EditUserActionSupport.MODEL_SESSION_KEY));
	}
}
