package com.gb1.healthcheck.domain.users;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * A user validator used for user updates. It executes a full check on the available properties.
 * This includes checking that the provided email address is not already owned by existing users.
 * 
 * @author Guillaume Bilodeau
 */
@Component("userUpdateValidator")
public class FullUserUpdateValidator implements UserValidator {
	@Resource
	protected UserRepository userRepo;

	public FullUserUpdateValidator() {
	}

	/**
	 * Validates the given user.
	 * 
	 * @param user The user to be validated
	 * @throws UserException When a property must be refused
	 */
	public void validate(User user) throws UserException {
		if (isEmailAlreadyOwned(user)) {
			throw new EmailAlreadyExistsException(user.getEmail());
		}
	}

	private boolean isEmailAlreadyOwned(User user) {
		boolean owned;

		// make sure that no one owns the user's email address, aside from
		// the user himself

		List<User> owners = userRepo.findUsersByEmail(user.getEmail());
		if (owners.isEmpty()) {
			owned = false;
		}
		else if (owners.size() == 1 && owners.get(0).equals(user)) {
			owned = false;
		}
		else {
			owned = true;
		}

		return owned;
	}
}
