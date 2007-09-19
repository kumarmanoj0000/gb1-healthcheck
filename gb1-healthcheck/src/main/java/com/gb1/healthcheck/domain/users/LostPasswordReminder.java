package com.gb1.healthcheck.domain.users;

/**
 * Sends a reminder to a given user containing his lost password.
 * 
 * @author Guillaume Bilodeau
 */
public interface LostPasswordReminder {
	/**
	 * Sends a reminder to the given user containing his lost password.
	 * 
	 * @param user The user who lost his password
	 */
	void remindLostPassword(User user);
}
