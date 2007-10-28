package com.gb1.healthcheck.domain.users;

/**
 * A source of all of a user's properties. Completes the {@link UserMutablePropertyProvider} by
 * adding a user's immutable properties.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserPropertyProvider extends UserMutablePropertyProvider {
	/**
	 * Returns the user's login.
	 * 
	 * @return The user's login
	 */
	String getLogin();

	/**
	 * Returns the user's password.
	 * 
	 * @return The user's password
	 */
	String getPassword();
}
