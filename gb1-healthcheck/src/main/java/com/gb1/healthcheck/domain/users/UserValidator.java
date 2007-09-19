package com.gb1.healthcheck.domain.users;

/**
 * A user validator.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserValidator {
	/**
	 * Validates the given user.
	 * 
	 * @param user The user to be validated
	 * @throws UserException When a property must be refused
	 */
	void validate(User user) throws UserException;
}
