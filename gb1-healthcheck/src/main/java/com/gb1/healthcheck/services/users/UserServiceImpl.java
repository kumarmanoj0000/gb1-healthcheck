package com.gb1.healthcheck.services.users;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserActivationRequester;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.UserValidator;

/**
 * The default implementation of the user service.
 * 
 * @author Guillaume Bilodeau
 */
@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private UserValidator userCreationValidator;
	private UserValidator userUpdateValidator;

	private UserActivationRequester userActivationRequester;
	private LostPasswordReminder lostPasswordReminder;

	public UserServiceImpl() {
	}

	public UserActivationRequest registerUser(UserRegistrationRequest request) throws UserException {
		User user = new User(request);
		userCreationValidator.validate(user);
		UserActivationRequest actRequest = userActivationRequester.requestUserActivation(user);
		userRepository.saveUser(actRequest.getPendingUser());

		return actRequest;
	}

	public User activateUser(String email, Token candidateActivationToken)
			throws UnknownUserException, UserActivationException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new UnknownUserException();
		}

		user.activate(candidateActivationToken);

		return user;
	}

	public User updateUser(UserUpdateRequest request) throws UserException {
		User user = userRepository.loadUser(request.getUserId());
		if (user == null) {
			throw new UnknownUserException();
		}

		user.update(request);
		userUpdateValidator.validate(user);

		return user;
	}

	@Transactional(readOnly = true)
	public void sendLostPassword(String email) throws UnknownUserException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new UnknownUserException();
		}

		lostPasswordReminder.remindLostPassword(user);
	}

	@Transactional(readOnly = true)
	public User loadUser(Long userId) {
		User user = userRepository.loadUser(userId);
		return user;
	}

	@Transactional(readOnly = true)
	public User findUserByLogin(String login) {
		User user = userRepository.findUserByLogin(login);
		return user;
	}

	// external dependencies

	public void setUserRepository(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	public void setUserCreationValidator(UserValidator validator) {
		this.userCreationValidator = validator;
	}

	public void setUserUpdateValidator(UserValidator validator) {
		this.userUpdateValidator = validator;
	}

	public void setUserActivationRequester(UserActivationRequester activationRequester) {
		this.userActivationRequester = activationRequester;
	}

	public void setLostPasswordSender(LostPasswordReminder lostPasswordReminder) {
		this.lostPasswordReminder = lostPasswordReminder;
	}
}
