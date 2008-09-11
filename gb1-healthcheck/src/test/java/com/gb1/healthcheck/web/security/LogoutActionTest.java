package com.gb1.healthcheck.web.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.Action;

public class LogoutActionTest {
	@Test
	public void testExecute() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		LogoutAction action = new LogoutAction();
		action.setServletRequest(request);
		action.setServletResponse(response);
		assertEquals(Action.SUCCESS, action.execute());
	}
}
