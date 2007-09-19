package com.gb1.healthcheck.web.users;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserMutablePropertyProvider;

/**
 * A request to udpate a user's details.
 * 
 * @author Guillaume Bilodeau
 */
public class UserUpdateRequest implements UserMutablePropertyProvider {
	/**
	 * The updated user's ID
	 */
	private Long userId;

	/**
	 * The updated user's login
	 */
	private String login;

	/**
	 * The updated user's email
	 */
	private String email;

	/**
	 * The updated user's roles
	 */
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * Package-protected constructor for exposed unit test classes.
	 */
	UserUpdateRequest() {
	}

	/**
	 * Creates a new update request for the given user.
	 * 
	 * @param user The user to update
	 */
	public UserUpdateRequest(User user) {
		Validate.notNull(user);

		userId = user.getId();
		login = user.getLogin();
		email = user.getEmail();
	}

	/**
	 * Returns the updated user's ID.
	 * 
	 * @return The updated user's ID
	 */
	public Long getId() {
		return userId;
	}

	/**
	 * Sets the updated user's ID. To be used solely by unit tests.
	 * 
	 * @param userId The updated user's ID
	 */
	void setId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the updated user's login.
	 * 
	 * @return The updated user's login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Returns the updated user's email address.
	 * 
	 * @return The updated user's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the updated user's new email address.
	 * 
	 * @param email The updated user's new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the updated user's new roles.
	 * 
	 * @return The updated user's new roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}
}
