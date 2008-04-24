package com.gb1.struts2.dispatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * A result that participates in implementing flash scope. Before redirecting to a given namespaced
 * action, it creates a "flash map" from properties retrieved from the current value stack and saves
 * it in the user session so that it survives the HTTP redirect and gets picked up by the flash
 * interceptor.
 * 
 * Clearly this implementation is very similar to {@link ServletActionRedirectResult}. Some
 * refactoring should be done.
 * 
 * @author Guillaume Bilodeau
 */
public class FlashResult extends ServletRedirectResult {
	public static final String DEFAULT_SESSION_KEY = "__flashMap";
	public static final String DEFAULT_PARAM = "actionName";

	private String sessionKey = DEFAULT_SESSION_KEY;
	private String actionName;
	private String namespace;
	private String method;
	private boolean parse = true;
	private List<String> prohibitedResultParams = Arrays.asList(new String[] { DEFAULT_PARAM,
			"namespace", "method", "encode", "parse", "location", "prependServletContext" });

	public FlashResult() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(ActionInvocation invocation) throws Exception {
		String finalLocation = evaluateFinalLocation(invocation);
		setLocation(finalLocation);

		Map<String, Object> flashMap = buildFlashMap(invocation);
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		session.put(sessionKey, flashMap);

		super.execute(invocation);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> buildFlashMap(ActionInvocation invocation) {
		Map<String, Object> flashMap = new HashMap<String, Object>();

		if (invocation.getResultCode() != null) {
			ResultConfig resultConfig = invocation.getProxy().getConfig().getResults().get(
					invocation.getResultCode());
			Map resultConfigParams = resultConfig.getParams();

			for (Iterator i = resultConfigParams.entrySet().iterator(); i.hasNext();) {
				Map.Entry e = (Map.Entry) i.next();
				String key = (String) e.getKey();

				if (!prohibitedResultParams.contains(key)) {
					Object value;

					if (parse) {
						value = TextParseUtil.translateVariables('$', (String) e.getValue(),
								invocation.getStack(), Object.class);
					}
					else {
						value = e.getValue();
					}

					flashMap.put(key, value);
				}
			}
		}
		return flashMap;
	}

	private String evaluateFinalLocation(ActionInvocation invocation) {
		actionName = conditionalParse(actionName, invocation);

		if (namespace == null) {
			namespace = invocation.getProxy().getNamespace();
		}
		else {
			namespace = conditionalParse(namespace, invocation);
		}

		if (method == null) {
			method = "";
		}
		else {
			method = conditionalParse(method, invocation);
		}

		String finalLocation = actionMapper.getUriFromActionMapping(new ActionMapping(actionName,
				namespace, method, null));
		return finalLocation;
	}

	@Override
	public void setParse(boolean parse) {
		this.parse = parse;
		super.setParse(parse);
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
