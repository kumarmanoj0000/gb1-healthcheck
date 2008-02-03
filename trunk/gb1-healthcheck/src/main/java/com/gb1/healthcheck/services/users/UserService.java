package com.gb1.healthcheck.services.users;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserException;

/**
 * A facade for all services related to user management.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserService {
	/**
	 * Loads the user identified by the given ID. If no user corresponds to this ID, null is
	 * returned.
	 * 
	 * @param userId The ID of the user to load
	 * @return The corresponding user; null if not found
	 */
	User loadUser(Long userId);

	/**
	 * Finds a user identified by the given login name. If no user corresponds to this login name,
	 * null is returned.
	 * 
	 * @param login The login name of the user to find
	 * @return The corresponding user; null if not found
	 */
	User findUserByLogin(String login);

	/**
	 * Registers a new user based on the given registration request. Following registration, the
	 * created user is not yet activated. An activation request will be sent to him in order to
	 * validate his registration and activate his account.
	 * 
	 * @param request The request for user registration
	 * @return The user activation request that will be sent to the user
	 * @throws UserException When registration fails
	 */
	UserActivationRequest registerUser(UserRegistrationRequest request) throws UserException;

	/**
	 * Activates a user account. The user will be activated if his account can be retrieved using
	 * the given email address and if the submitted token is valid.
	 * 
	 * @param email The user's email address
	 * @param candidateActivationToken The activation token submitted by the candidate user
	 * @return The activated user
	 * @throws UnknownUserException When no user corresponds to the given email address
	 * @throws UserActivationException When activation fails
	 */
	User activateUser(String email, Token candidateActivationToken) throws UnknownUserException,
			UserActivationException;

	/**
	 * Updates a user based on the given update request.
	 * 
	 * @param userId The ID of the user to be updated
	 * @param request The request to update
	 * @return The updated user
	 * @throws UserException If the user to update doesn't exist
	 */
	User updateUser(Long userId, UserUpdateRequest request) throws UserException;

	/**
	 * Sends a user's lost password to his email address.
	 * 
	 * @param email The user's email address
	 * @throws UnknownUserException When no registered user owns this address
	 */
	void sendLostPassword(String email) throws UnknownUserException;
}
