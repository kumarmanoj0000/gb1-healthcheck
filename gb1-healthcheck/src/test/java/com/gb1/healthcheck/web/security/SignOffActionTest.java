package com.gb1.healthcheck.web.security;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.Action;

public class SignOffActionTest extends TestCase {
	public void testExecute() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		SignOffAction action = new SignOffAction();
		action.setServletRequest(request);
		action.setServletResponse(response);
		assertEquals(Action.SUCCESS, action.execute());
	}
}
