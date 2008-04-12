package com.gb1.healthcheck.web.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.struts2.views.jsp.TagUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * A set of convenience methods for querying and updating the HTTP request.
 * 
 * @author Guillaume Bilodeau
 */
public class HttpRequestUtils {
	private HttpRequestUtils() {
	}

	// TODO Move to more appropriate utility class
	public static Locale resolveRequestLocale() {
		Locale result = null;
		ValueStack stack = ActionContext.getContext().getValueStack();

		for (Iterator it = stack.getRoot().iterator(); it.hasNext();) {
			Object o = it.next();

			if (o instanceof LocaleProvider) {
				LocaleProvider lp = (LocaleProvider) o;
				result = lp.getLocale();
				break;
			}
		}

		if (result == null) {
			result = Locale.getDefault();
		}

		return result;
	}

	// TODO Move to more appropriate utility class
	public static String resolveResourceKey(String resourceKey, String defaultValue,
			PageContext pageContext) {
		String message = null;
		String key = (resourceKey != null) ? resourceKey : defaultValue;

		OgnlValueStack stack = (OgnlValueStack) TagUtils.getStack(pageContext);
		for (Iterator it = stack.getRoot().iterator(); it.hasNext();) {
			Object o = it.next();

			if (o instanceof TextProvider) {
				TextProvider tp = (TextProvider) o;
				message = tp.getText(key, defaultValue, Collections.EMPTY_LIST, stack);
				break;
			}
		}

		if (message == null && resourceKey != null) {
			message = "???" + resourceKey + "???";
		}

		return message;
	}
}
