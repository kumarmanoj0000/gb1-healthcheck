package com.gb1.healthcheck.domain.users;

import java.util.List;

/**
 * A repository for users.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserRepository {
	/**
	 * Loads the user identified by the given ID. If no user corresponds to this ID, null is
	 * returned.
	 * 
	 * @param userId The ID of the user to load
	 * @return The corresponding user; null if not found
	 */
	User loadUser(Long userId);

	/**
	 * Saves the given user.
	 * 
	 * @param user The user to save
	 */
	void saveUser(User user);

	/**
	 * Finds all registered users.
	 * 
	 * @return The list of registered users
	 */
	List<User> findUsers();

	/**
	 * Finds a subset of matching registered users, based on inclusive indexes.
	 * 
	 * @param fromIndex The index of the first user to return
	 * @param toIndex The index of the last user to return
	 * @return The matching registered users
	 */
	List<User> findUsers(int fromIndex, int toIndex);

	/**
	 * Finds a user identified by the given login name. If no user corresponds to this login name,
	 * null is returned.
	 * 
	 * @param login The login name of the user to find
	 * @return The corresponding user; null if not found
	 */
	User findUserByLogin(String login);

	/**
	 * Finds a user identified by the given email address. If no user corresponds to this email
	 * address, null is returned.
	 * 
	 * @param email The email address of the user to find
	 * @return The corresponding user; null if not found
	 */
	User findUserByEmail(String email);

	/**
	 * Finds all users who tentatively own the given email address. This is possible when a user has
	 * just been updated with an already owned email address and the transaction has not yet been
	 * committed.
	 * 
	 * @param email The email address of the users to find
	 * @return The corresponding users; empty if not found
	 */
	List<User> findUsersByEmail(String email);

	/**
	 * Retrieves the number of registered users, whatever their status.
	 * 
	 * @return The number of registered users
	 */
	int getUserCount();
}