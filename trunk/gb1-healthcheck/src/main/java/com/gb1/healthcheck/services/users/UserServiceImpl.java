package com.gb1.healthcheck.services.users;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.pagination.DelegatingScrollablePaginatedList;
import com.gb1.commons.pagination.PaginatedListItemProvider;
import com.gb1.commons.pagination.ScrollablePaginatedList;
import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserActivationRequester;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.domain.users.UserMutablePropertyProvider;
import com.gb1.healthcheck.domain.users.UserPropertyProvider;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.UserValidator;

/**
 * The default implementation of the user service.
 * 
 * @author Guillaume Bilodeau
 */
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private UserValidator userCreationValidator;
	private UserValidator userUpdateValidator;

	private UserActivationRequester userActivationRequester;
	private LostPasswordReminder lostPasswordReminder;

	public UserServiceImpl() {
	}

	/**
	 * Registers a new user based on the given property provider. Following registration, the
	 * created user is not yet activated. An activation request will be sent to him in order to
	 * validate his registration and activate his account.
	 * 
	 * @param propertyProvider The provider of properties to be used for user creation
	 * @return The user activation request that will be sent to the user
	 * @throws UserException When registration fails
	 */
	@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
	public UserActivationRequest registerUser(UserPropertyProvider propertyProvider)
			throws UserException {
		User user = new User(propertyProvider);
		userCreationValidator.validate(user);
		UserActivationRequest actRequest = userActivationRequester.requestUserActivation(user);
		userRepository.saveUser(actRequest.getPendingUser());

		return actRequest;
	}

	/**
	 * Activates a user account. The user will be activated if his account can be retrieved using
	 * the given email address and if the submitted token is valid.
	 * 
	 * @param email The user's email address
	 * @param candidateActivationToken The activation token submitted by the candidate user
	 * @return The activated user
	 * @throws UnknownUserException When no user corresponds to the given email address
	 * @throws UserActivationException When activation fails
	 */
	@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
	public User activateUser(String email, Token candidateActivationToken)
			throws UnknownUserException, UserActivationException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new UnknownUserException();
		}

		user.activate(candidateActivationToken);

		return user;
	}

	/**
	 * Updates a user based on the given property provider.
	 * 
	 * @param propertyProvider The provider of properties to be updated
	 * @return The updated user
	 * @throws UserException If the user to update doesn't exist
	 */
	@Transactional(rollbackFor = { RuntimeException.class, UserException.class })
	public User updateUser(Long userId, UserMutablePropertyProvider propertyProvider)
			throws UserException {
		User user = userRepository.loadUser(userId);
		if (user == null) {
			throw new UnknownUserException();
		}

		user.update(propertyProvider);
		userUpdateValidator.validate(user);

		return user;
	}

	/**
	 * Sends a user's lost password to his email address.
	 * 
	 * @param email The user's email address
	 * @throws UnknownUserException When no registered user owns this address
	 */
	@Transactional(readOnly = true)
	public void sendLostPassword(String email) throws UnknownUserException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new UnknownUserException();
		}

		lostPasswordReminder.remindLostPassword(user);
	}

	/**
	 * Loads the user identified by the given ID. If no user corresponds to this ID, null is
	 * returned.
	 * 
	 * @param userId The ID of the user to load
	 * @return The corresponding user; null if not found
	 */
	@Transactional(readOnly = true)
	public User loadUser(Long userId) {
		User user = userRepository.loadUser(userId);
		return user;
	}

	/**
	 * Lists all registered users.
	 * 
	 * @return The list of registered users
	 */
	@Transactional(readOnly = true)
	public ScrollablePaginatedList<User> listUsersPaginated(int pageSize) {
		PaginatedListItemProvider<User> userProvider = new PaginatedListItemProvider<User>() {
			private int cachedSize = -1;

			public int size() {
				if (cachedSize == -1) {
					cachedSize = userRepository.getUserCount();
				}
				return cachedSize;
			}

			public List<User> items(int fromIndex, int toIndex) {
				return userRepository.findUsers(fromIndex, toIndex);
			}
		};

		ScrollablePaginatedList<User> userList = new DelegatingScrollablePaginatedList<User>(
				userProvider, pageSize);
		return userList;
	}

	/**
	 * Finds a user identified by the given login name. If no user corresponds to this login name,
	 * null is returned.
	 * 
	 * @param login The login name of the user to find
	 * @return The corresponding user; null if not found
	 */
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
