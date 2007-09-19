package com.gb1.healthcheck.domain.users;

import java.util.Set;

/**
 * A source of a user's mutable properties.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserMutablePropertyProvider {
	/**
	 * Returns the user's email address.
	 * 
	 * @return The user's email address
	 */
	String getEmail();

	/**
	 * Returns the roles to be assigned to the user.
	 * 
	 * @return The roles to be assigned
	 */
	Set<Role> getRoles();
}
