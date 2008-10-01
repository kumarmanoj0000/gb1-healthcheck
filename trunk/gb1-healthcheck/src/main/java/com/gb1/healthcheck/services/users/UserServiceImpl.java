package com.gb1.healthcheck.services.users;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.PasswordGenerator;
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
	@Resource
	protected UserRepository userRepository;

	@Resource
	protected UserValidator userCreationValidator;

	@Resource
	protected UserValidator userUpdateValidator;

	@Resource
	protected UserActivationRequester userActivationRequester;

	@Resource
	protected LostPasswordReminder lostPasswordReminder;

	@Resource
	protected PasswordGenerator passwordGenerator;

	@Resource
	protected PasswordResetNotifier passwordResetNotifier;

	private int generatedPasswordLength;

	public UserServiceImpl() {
	}

	public UserActivationRequest registerUser(User user) throws UserException {
		userCreationValidator.validate(user);
		UserActivationRequest actRequest = userActivationRequester.requestUserActivation(user);
		userRepository.persistUser(actRequest.getPendingUser());

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

	public void updateUser(User user) throws UserException {
		userUpdateValidator.validate(user);
		userRepository.mergeUser(user);
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
		return userRepository.loadUser(userId);
	}

	@Transactional(readOnly = true)
	public User findUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findUsers();
	}

	public void deleteUsers(List<Long> userIds) {
		for (Long userId : userIds) {
			User user = userRepository.loadUser(userId);
			userRepository.deleteUser(user);
		}
	}

	public void changeUserPassword(Long userId, String currentPassword, String newPassword)
			throws InvalidPasswordException {
		User user = userRepository.loadUser(userId);
		user.changePassword(currentPassword, newPassword);
	}

	public void resetUserPassword(Long userId) {
		User user = userRepository.loadUser(userId);
		user.resetPassword(passwordGenerator.generatePassword(generatedPasswordLength));

		passwordResetNotifier.notifyPasswordReset(user);
	}

	@Resource
	public void setGlobalConstants(Map<String, String> constants) {
		generatedPasswordLength = Integer.parseInt(constants.get("user.generatedPasswordLength"));
	}
}
