package com.gb1.healthcheck.services.users;

import java.util.List;

import com.gb1.healthcheck.core.Token;
import com.gb1.healthcheck.domain.users.InvalidPasswordException;
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
	User getUser(Long userId);

	/**
	 * Finds a user identified by the given login name. If no user corresponds to this login name,
	 * null is returned.
	 * 
	 * @param login The login name of the user to find
	 * @return The corresponding user; null if not found
	 */
	User findUserByLogin(String login);

	/**
	 * Registers a new user. Following registration, the created user is not yet activated. An
	 * activation request will be sent to him in order to validate his registration and activate his
	 * account.
	 * 
	 * @param user The user to register
	 * @return The user activation request that will be sent to the user
	 * @throws UserException When registration fails
	 */
	UserActivationRequest registerUser(User user) throws UserException;

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
	 * Updates the given user.
	 * 
	 * @param user The user to update
	 * @throws UserException If the user to update doesn't exist
	 */
	void updateUser(User user) throws UserException;

	/**
	 * Sends a user's lost password to his email address.
	 * 
	 * @param email The user's email address
	 * @throws UnknownUserException When no registered user owns this address
	 */
	void sendLostPassword(String email) throws UnknownUserException;

	/**
	 * Returns all registered users in the system, regardless of their status.
	 * 
	 * @return All registered system users
	 */
	List<User> getAllUsers();

	/**
	 * Deletes all users identified by the given IDs. If no user corresponds to a given ID, this ID
	 * is ignored.
	 * 
	 * @param userIds The IDs of the users to delete
	 */
	void deleteUsers(List<Long> userIds);

	/**
	 * Changes a user's password.
	 * 
	 * @param userId The ID of the user whose password will be changed
	 * @param currentPassword The user's current password
	 * @param newPassword The user's new password
	 * @throws InvalidPasswordException If the current password is wrong
	 */
	void changeUserPassword(Long userId, String currentPassword, String newPassword)
			throws InvalidPasswordException;

	/**
	 * Resets a user's password.
	 * 
	 * @param userId The ID of the user whose password will be reset
	 */
	void resetUserPassword(Long userId);
}
