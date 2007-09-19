package com.gb1.healthcheck.domain.users;

import org.apache.commons.lang.StringUtils;

/**
 * A user validator used during initial creation. It executes a full check on the available
 * properties. This includes checking that the provided login name and email address are not already
 * owned by existing users.
 * 
 * @author Guillaume Bilodeau
 */
public class FullUserCreationValidator implements UserValidator {
	private UserRepository userRepository;

	public FullUserCreationValidator() {
	}

	/**
	 * Validates the given user.
	 * 
	 * @param user The user to be validated
	 * @throws UserException When a property must be refused
	 */
	public void validate(User user) throws UserException {
		if (StringUtils.isEmpty(user.getLogin())) {
			throw new InvalidLoginException(user.getLogin());
		}

		if (StringUtils.isEmpty(user.getEmail())) {
			throw new InvalidEmailException(user.getEmail());
		}

		if (StringUtils.isEmpty(user.getPassword())) {
			throw new InvalidPasswordException(user.getPassword());
		}

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

	public void setUserRepository(UserRepository userRepo) {
		this.userRepository = userRepo;
	}
}
