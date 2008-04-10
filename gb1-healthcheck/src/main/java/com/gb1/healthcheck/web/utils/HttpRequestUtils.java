package com.gb1.healthcheck.web.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.struts2.views.jsp.TagUtils;

import com.gb1.healthcheck.domain.users.AcegiUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;
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

	/**
	 * Returns the connected user. If no user is found, returns null.
	 * 
	 * @return The connected user; null if not found
	 */
	public static User getUser() {
		User user = null;
		AcegiUserDetailsAdapter acegiUser = (AcegiUserDetailsAdapter) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		if (acegiUser != null) {
			user = acegiUser.getUser();
		}

		return user;
	}

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
