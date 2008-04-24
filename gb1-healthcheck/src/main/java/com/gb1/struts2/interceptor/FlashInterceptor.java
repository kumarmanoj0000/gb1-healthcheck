package com.gb1.struts2.interceptor;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.OgnlUtil;
import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * An interceptor that participates in implementing flash scope. It retrieves the "flash map" that
 * was put in the session by a previously executed flash result and tries to set all its values on
 * the action about to be executed.
 * 
 * @author Guillaume Bilodeau
 */
public class FlashInterceptor extends AbstractInterceptor {
	private String sessionKey = FlashResult.DEFAULT_SESSION_KEY;

	public FlashInterceptor() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> sessionMap = (Map<String, Object>) invocation.getInvocationContext()
				.get(ActionContext.SESSION);
		Map<String, Object> flashMap = (Map<String, Object>) sessionMap.get(sessionKey);

		if (flashMap != null) {
			Object target = invocation.getAction();
			Map targetCtx = Ognl.createDefaultContext(target);
			Ognl.setTypeConverter(targetCtx, XWorkConverter.getInstance());

			for (String key : flashMap.keySet()) {
				Object expr = OgnlUtil.compile(key);
				Object value = flashMap.get(key);

				try {
					Ognl.setValue(expr, targetCtx, target, value);
				}
				catch (OgnlException ignore) {
				}
			}
		}

		// the flash map must survive only a single request, which corresponds to a single intercept
		sessionMap.remove(sessionKey);

		String result = invocation.invoke();
		return result;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
