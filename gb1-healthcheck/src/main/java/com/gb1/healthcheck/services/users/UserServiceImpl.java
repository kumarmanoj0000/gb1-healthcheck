package com.gb1.healthcheck.services.users;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.PasswordResetNotifier;
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
@Service("userService")
@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserValidator userCreationValidator;
	private UserValidator userUpdateValidator;

	private UserActivationRequester userActivationRequester;
	private LostPasswordReminder lostPasswordReminder;
	private PasswordResetNotifier passwordResetNotifier;

	public UserServiceImpl() {
	}

	public UserActivationRequest registerUser(UserRegistrationRequest request) throws UserException {
		User user = userAssembler.createUser(request);
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

		userAssembler.updateMeal(user, request);
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
	public User getUser(Long userId) {
		User user = userRepository.loadUser(userId);
		return user;
	}

	@Transactional(readOnly = true)
	public User findUserByLogin(String login) {
		User user = userRepository.findUserByLogin(login);
		return user;
	}

	@Transactional(readOnly = true)
	public Set<User> getAllUsers() {
		return userRepository.findUsers();
	}

	public void deleteUsers(Set<Long> userIds) {
		userRepository.deleteUsers(userIds);
	}

	public void changeUserPassword(Long userId, String currentPassword, String newPassword)
			throws InvalidPasswordException {
		User user = userRepository.loadUser(userId);
		user.changePassword(currentPassword, newPassword);
	}

	public void resetUserPassword(Long userId) {
		User user = userRepository.loadUser(userId);
		user.resetPassword();

		passwordResetNotifier.notifyPasswordReset(user);
	}

	// external dependencies

	@Resource
	public void setUserRepository(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Resource
	public void setUserAssembler(UserAssembler userAssembler) {
		this.userAssembler = userAssembler;
	}

	@Resource
	public void setUserCreationValidator(UserValidator validator) {
		this.userCreationValidator = validator;
	}

	@Resource
	public void setUserUpdateValidator(UserValidator validator) {
		this.userUpdateValidator = validator;
	}

	@Resource
	public void setUserActivationRequester(UserActivationRequester activationRequester) {
		this.userActivationRequester = activationRequester;
	}

	@Resource
	public void setLostPasswordReminder(LostPasswordReminder lostPasswordReminder) {
		this.lostPasswordReminder = lostPasswordReminder;
	}

	@Resource
	public void setPasswordResetNotifier(PasswordResetNotifier notifier) {
		this.passwordResetNotifier = notifier;
	}
}
