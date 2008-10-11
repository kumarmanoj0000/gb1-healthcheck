package com.gb1.healthcheck.domain.users;

import java.util.List;

public interface UserRepository {
	User findUser(Long userId);

	User findUserByLogin(String login);

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

	List<User> findAllUsers();

	void persist(User user);

	void merge(User user);

	/**
	 * Deletes the given user.
	 * 
	 * @param user The user to delete
	 */
	void delete(User user);
}
