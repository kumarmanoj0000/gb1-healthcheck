package com.gb1.healthcheck.domain.users;

/**
 * A role that can be assigned to a user.
 * 
 * @author Guillaume Bilodeau
 */
public enum Role {
	ADMINISTRATOR("ROLE_ADMINISTRATOR"), STANDARD("ROLE_STANDARD");

	/**
	 * The role's name
	 */
	private String name;

	/**
	 * Creates a new identified, named role.
	 * 
	 * @param name The role's name
	 */
	private Role(String name) {
		this.name = name;
	}

	/**
	 * Returns the role's name.
	 * 
	 * @return The role's name
	 */
	public String getName() {
		return name;
	}
}
