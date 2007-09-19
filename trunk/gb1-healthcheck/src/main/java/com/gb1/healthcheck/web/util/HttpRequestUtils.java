package com.gb1.healthcheck.web.util;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.context.SecurityContextHolder;

import com.gb1.healthcheck.domain.users.AcegiUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;

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
	 * @param request The HTTP request
	 * @return The connected user; null if not found
	 */
	public static User getUser(HttpServletRequest request) {
		User user = null;
		AcegiUserDetailsAdapter acegiUser = (AcegiUserDetailsAdapter) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		if (acegiUser != null) {
			user = acegiUser.getUser();
		}

		return user;
	}
}
