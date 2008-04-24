package com.gb1.struts2.dispatcher;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.opensymphony.xwork2.mock.MockActionProxy;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;

public class FlashResultTest extends TestCase {
	private Date today = new Date();

	public FlashResultTest() {
	}

	@SuppressWarnings("unchecked")
	public void testExecuteNoParams() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map sessionMap = new HashMap();

		Map contextMap = sessionMap;
		contextMap.put(StrutsStatics.HTTP_REQUEST, request);
		contextMap.put(StrutsStatics.HTTP_RESPONSE, response);
		contextMap.put(ActionContext.SESSION, sessionMap);

		Map<String, ResultConfig> results = new HashMap<String, ResultConfig>();
		results.put(Action.SUCCESS, new ResultConfig());

		TestAction action = new TestAction();
		ActionConfig config = new ActionConfig();
		config.setResults(results);

		MockActionProxy proxy = new MockActionProxy();
		proxy.setAction(action);
		proxy.setConfig(config);

		MockActionInvocation ai = new MockActionInvocation();
		ai.setInvocationContext(new ActionContext(contextMap));
		ai.setAction(action);
		ai.setProxy(proxy);
		ai.setStack(new OgnlValueStack());
		ai.setResultCode(Action.SUCCESS);

		FlashResult result = new FlashResult();
		result.setActionMapper(new DefaultActionMapper());
		result.setNamespace("/users");
		result.setActionName("editUser");

		result.execute(ai);
		assertEquals("/users/editUser.action", response.getRedirectedUrl());

		Map flashMap = (Map) sessionMap.get(FlashResult.DEFAULT_SESSION_KEY);
		assertTrue(flashMap.isEmpty());
	}

	@SuppressWarnings("unchecked")
	public void testExecuteParseTrue() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map sessionMap = new HashMap();

		Map contextMap = sessionMap;
		contextMap.put(StrutsStatics.HTTP_REQUEST, request);
		contextMap.put(StrutsStatics.HTTP_RESPONSE, response);
		contextMap.put(ActionContext.SESSION, sessionMap);

		TestAction action = new TestAction();
		ValueStack stack = new OgnlValueStack();
		stack.push(action);

		ResultConfig resultConfig = new ResultConfig();
		resultConfig.addParam("param1", "${param1}");
		resultConfig.addParam("param2", "${param2}");
		resultConfig.addParam("param3", "${param3}");
		resultConfig.addParam("param4", "${param4}");
		resultConfig.addParam("param5", "paramValue5");

		Map<String, ResultConfig> results = new HashMap<String, ResultConfig>();
		results.put(Action.SUCCESS, resultConfig);

		ActionConfig config = new ActionConfig();
		config.setResults(results);

		MockActionProxy proxy = new MockActionProxy();
		proxy.setAction(action);
		proxy.setConfig(config);

		MockActionInvocation ai = new MockActionInvocation();
		ai.setInvocationContext(new ActionContext(contextMap));
		ai.setStack(stack);
		ai.setAction(action);
		ai.setProxy(proxy);
		ai.setResultCode(Action.SUCCESS);

		FlashResult result = new FlashResult();
		result.setActionMapper(new DefaultActionMapper());
		result.setNamespace("/users");
		result.setActionName("editUser");
		result.setParse(true);

		result.execute(ai);
		assertEquals("/users/editUser.action", response.getRedirectedUrl());

		Map flashMap = (Map) sessionMap.get(FlashResult.DEFAULT_SESSION_KEY);
		assertEquals(action.getParam1(), flashMap.get("param1"));
		assertEquals(action.getParam2(), flashMap.get("param2"));
		assertEquals(action.getParam3(), flashMap.get("param3"));
		assertEquals(action.getParam4(), flashMap.get("param4"));
		assertEquals("paramValue5", flashMap.get("param5"));
	}

	@SuppressWarnings("unchecked")
	public void testExecuteParseFalse() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map sessionMap = new HashMap();

		Map contextMap = sessionMap;
		contextMap.put(StrutsStatics.HTTP_REQUEST, request);
		contextMap.put(StrutsStatics.HTTP_RESPONSE, response);
		contextMap.put(ActionContext.SESSION, sessionMap);

		TestAction action = new TestAction();
		ValueStack stack = new OgnlValueStack();
		stack.push(action);

		ResultConfig resultConfig = new ResultConfig();
		resultConfig.addParam("param1", "${param1}");
		resultConfig.addParam("param2", "${param2}");
		resultConfig.addParam("param3", "${param3}");
		resultConfig.addParam("param4", "${param4}");
		resultConfig.addParam("param5", "paramValue5");

		Map<String, ResultConfig> results = new HashMap<String, ResultConfig>();
		results.put(Action.SUCCESS, resultConfig);

		ActionConfig config = new ActionConfig();
		config.setResults(results);

		MockActionProxy proxy = new MockActionProxy();
		proxy.setAction(action);
		proxy.setConfig(config);

		MockActionInvocation ai = new MockActionInvocation();
		ai.setInvocationContext(new ActionContext(contextMap));
		ai.setStack(stack);
		ai.setAction(action);
		ai.setProxy(proxy);
		ai.setResultCode(Action.SUCCESS);

		FlashResult result = new FlashResult();
		result.setActionMapper(new DefaultActionMapper());
		result.setNamespace("/users");
		result.setActionName("editUser");
		result.setParse(false);

		result.execute(ai);
		assertEquals("/users/editUser.action", response.getRedirectedUrl());

		Map flashMap = (Map) sessionMap.get(FlashResult.DEFAULT_SESSION_KEY);
		assertEquals("${param1}", flashMap.get("param1"));
		assertEquals("${param2}", flashMap.get("param2"));
		assertEquals("${param3}", flashMap.get("param3"));
		assertEquals("${param4}", flashMap.get("param4"));
		assertEquals("paramValue5", flashMap.get("param5"));
	}

	private class TestAction {
		public TestAction() {
		}

		public String getParam1() {
			return "param1";
		}

		public int getParam2() {
			return 2;
		}

		public Date getParam3() {
			return today;
		}

		public List<String> getParam4() {
			return Collections.nCopies(10, "1");
		}
	}
}
