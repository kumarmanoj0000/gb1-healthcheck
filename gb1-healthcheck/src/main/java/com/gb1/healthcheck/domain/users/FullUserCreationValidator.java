package com.gb1.healthcheck.domain.users;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * A user validator used during initial creation. It executes a full check on the available
 * properties. This includes checking that the provided login name and email address are not already
 * owned by existing users.
 * 
 * @author Guillaume Bilodeau
 */
@Component("userCreationValidator")
public class FullUserCreationValidator implements UserValidator {
	@Resource
	protected UserRepository userRepository;

	public FullUserCreationValidator() {
	}

	/**
	 * Validates the given user.
	 * 
	 * @param user The user to be validated
	 * @throws UserException When a property must be refused
	 */
	public void validate(User user) throws UserException {
		if (isLoginAlreadyTaken(user.getLogin())) {
			throw new LoginAlreadyExistsException(user.getLogin());
		}

		if (isEmailAlreadyTaken(user.getEmail())) {
			throw new EmailAlreadyExistsException(user.getEmail());
		}
	}

	private boolean isEmailAlreadyTaken(String email) {
		boolean taken = false;
		if (userRepository.findUserByEmail(email) != null) {
			taken = true;
		}

		return taken;
	}

	private boolean isLoginAlreadyTaken(String login) {
		boolean taken = false;
		if (userRepository.findUserByLogin(login) != null) {
			taken = true;
		}

		return taken;
	}
}
