package com.gb1.healthcheck.domain.users;

/**
 * A user activation requester controls how a given user is asked for activation.
 * 
 * @author Guillaume Bilodeau
 */
public interface UserActivationRequester {
	/**
	 * Requests activation for the specified user.
	 * 
	 * @param user The user to be activated
	 * @return The user activation request
	 */
	UserActivationRequest requestUserActivation(User user);
}
