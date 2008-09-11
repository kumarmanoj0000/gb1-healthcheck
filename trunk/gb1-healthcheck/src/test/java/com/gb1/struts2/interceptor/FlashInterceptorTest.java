package com.gb1.struts2.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;

public class FlashInterceptorTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testInterceptNoFlash() throws Exception {
		TestAction action = new TestAction();

		Map sessionMap = new HashMap();
		Map contextMap = sessionMap;
		contextMap.put(ActionContext.SESSION, sessionMap);

		MockActionInvocation ai = new MockActionInvocation();
		ai.setAction(action);
		ai.setResultCode(Action.SUCCESS);
		ai.setInvocationContext(new ActionContext(contextMap));

		FlashInterceptor interceptor = new FlashInterceptor();
		assertEquals(Action.SUCCESS, interceptor.intercept(ai));
		assertFalse(sessionMap.containsKey(FlashResult.DEFAULT_SESSION_KEY));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testIntercept() throws Exception {
		TestAction action = new TestAction();

		Map flashMap = new HashMap();
		flashMap.put("param1", "paramValue1");
		flashMap.put("param2", 42);
		flashMap.put("param3", null);
		flashMap.put("param4", Collections.nCopies(10, "1"));
		flashMap.put("param5", new Date());

		Map sessionMap = new HashMap();
		sessionMap.put(FlashResult.DEFAULT_SESSION_KEY, flashMap);

		Map contextMap = sessionMap;
		contextMap.put(ActionContext.SESSION, sessionMap);

		MockActionInvocation ai = new MockActionInvocation();
		ai.setAction(action);
		ai.setResultCode(Action.SUCCESS);
		ai.setInvocationContext(new ActionContext(contextMap));

		FlashInterceptor interceptor = new FlashInterceptor();
		assertEquals(Action.SUCCESS, interceptor.intercept(ai));
		assertFalse(sessionMap.containsKey(FlashResult.DEFAULT_SESSION_KEY));

		assertEquals("paramValue1", action.getParam1());
		assertEquals(42, action.getParam2());
		assertNull(action.getParam3());
		assertEquals(Collections.nCopies(10, "1"), action.getParam4());
	}

	private class TestAction {
		private String param1;
		private int param2;
		private Date param3;
		private List<String> param4;

		public TestAction() {
		}

		public String execute() {
			return Action.SUCCESS;
		}

		public String getParam1() {
			return param1;
		}

		public void setParam1(String param1) {
			this.param1 = param1;
		}

		public int getParam2() {
			return param2;
		}

		public void setParam2(int param2) {
			this.param2 = param2;
		}

		public Date getParam3() {
			return param3;
		}

		public void setParam3(Date param3) {
			this.param3 = param3;
		}

		public List<String> getParam4() {
			return param4;
		}

		public void setParam4(List<String> param4) {
			this.param4 = param4;
		}
	}
}
