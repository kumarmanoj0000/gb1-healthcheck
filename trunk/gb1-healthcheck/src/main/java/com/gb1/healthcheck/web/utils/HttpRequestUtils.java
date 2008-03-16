package com.gb1.healthcheck.web.utils;

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
}
