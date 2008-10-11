package com.gb1.healthcheck.services.users;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.core.Token;
import com.gb1.healthcheck.core.Validator;
import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.PasswordGenerator;
import com.gb1.healthcheck.domain.users.PasswordResetNotifier;
import com.gb1.healthcheck.domain.users.SpringUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserActivationRequester;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.domain.users.UserRepository;

/**
 * The default implementation of the user service.
 * 
 * @author Guillaume Bilodeau
 */
@Service("userService")
@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
public class UserServiceImpl implements UserService, UserDetailsService {
	@Resource
	protected UserRepository userRepository;

	@Resource
	protected Validator<User, UserException> userCreationValidator;

	@Resource
	protected Validator<User, UserException> userUpdateValidator;

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
		userRepository.persist(actRequest.getPendingUser());

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
		userRepository.merge(user);
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
	public User findUser(Long userId) {
		return userRepository.findUser(userId);
	}

	@Transactional(readOnly = true)
	public User findUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = null;
		User user = findUserByLogin(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		// authorities should be eagerly fetched
		userDetails = new SpringUserDetailsAdapter(user);
		userDetails.getAuthorities();

		return userDetails;
	}

	@Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return userRepository.findAllUsers();
	}

	public void deleteUsers(List<Long> userIds) {
		for (Long userId : userIds) {
			userRepository.delete(userRepository.findUser(userId));
		}
	}

	public void changeUserPassword(Long userId, String currentPassword, String newPassword)
			throws InvalidPasswordException {
		User user = userRepository.findUser(userId);
		user.changePassword(currentPassword, newPassword);
	}

	public void resetUserPassword(Long userId) {
		User user = userRepository.findUser(userId);
		user.resetPassword(passwordGenerator.generatePassword(generatedPasswordLength));
		passwordResetNotifier.notifyPasswordReset(user);
	}

	@Resource
	public void setGlobalConstants(Map<String, String> constants) {
		generatedPasswordLength = Integer.parseInt(constants.get("user.generatedPasswordLength"));
	}
}
