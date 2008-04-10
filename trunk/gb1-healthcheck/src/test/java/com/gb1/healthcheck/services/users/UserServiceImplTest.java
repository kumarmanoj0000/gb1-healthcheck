package com.gb1.healthcheck.services.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.PasswordGenerator;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserActivationRequester;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.UserValidator;
import com.gb1.healthcheck.domain.users.Users;

public class UserServiceImplTest extends TestCase {
	public void testRegisterUser() throws UserException {
		UserRegistrationRequest regRequest = new UserRegistrationRequest() {
			public Set<Role> getRoles() {
				return Collections.emptySet();
			}

			public String getLogin() {
				return "login";
			}

			public String getPassword() {
				return "1";
			}

			public String getEmail() {
				return null;
			}
		};

		UserValidator validator = EasyMock.createMock(UserValidator.class);
		validator.validate(EasyMock.isA(User.class));
		EasyMock.replay(validator);

		User expectedUser = new ExposedUser(regRequest);
		UserActivationRequest expectedActRequest = new UserActivationRequest(expectedUser, null);

		UserActivationRequester requester = EasyMock.createMock(UserActivationRequester.class);
		EasyMock.expect(requester.requestUserActivation(EasyMock.isA(User.class))).andReturn(
				expectedActRequest);
		EasyMock.replay(requester);

		UserRepository repo = EasyMock.createMock(UserRepository.class);
		repo.saveUser(EasyMock.isA(User.class));
		EasyMock.replay(repo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(repo);
		svc.setUserActivationRequester(requester);
		svc.setUserCreationValidator(validator);

		UserActivationRequest actRequest = svc.registerUser(regRequest);

		// make sure that the user was validated and saved, and that the activation request was sent
		assertEquals(expectedActRequest, actRequest);
		EasyMock.verify(validator);
		EasyMock.verify(repo);
		EasyMock.verify(requester);
	}

	public void testModifyUnknownUser() throws UserException {
		final User user = Users.gb();
		UserUpdateRequest modifReq = new UserUpdateRequest() {
			public Long getUserId() {
				return user.getId();
			}

			public String getEmail() {
				return user.getEmail();
			}

			public Set<Role> getRoles() {
				return user.getRoles();
			}
		};

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		try {
			svc.updateUser(modifReq);
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	public void testModifyUserOk() throws UserException {
		final ExposedUser originalUser = new ExposedUser();
		originalUser.setId(1L);
		originalUser.setLogin("user");
		originalUser.setEmail("user@gb.com");
		originalUser.setPassword("pass");

		UserUpdateRequest updateRequest = new UserUpdateRequest() {
			public Long getUserId() {
				return originalUser.getId();
			}

			public String getEmail() {
				return originalUser.getEmail();
			}

			public Set<Role> getRoles() {
				return originalUser.getRoles();
			}
		};

		UserValidator validator = EasyMock.createMock(UserValidator.class);
		validator.validate(originalUser);
		EasyMock.replay(validator);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(originalUser.getId())).andReturn(originalUser);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setUserUpdateValidator(validator);

		User modifiedUser = svc.updateUser(updateRequest);

		assertNotNull(modifiedUser);
		assertEquals(modifiedUser, originalUser);
		assertEquals(modifiedUser.getLogin(), originalUser.getLogin());
		assertEquals(modifiedUser.getEmail(), updateRequest.getEmail());
	}

	public void testSendLostPasswordOk() throws UnknownUserException {
		final String email = "user@gb.com";
		User u = new ExposedUser();
		u.setEmail(email);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(u);
		EasyMock.replay(userRepo);

		LostPasswordReminder sender = EasyMock.createMock(LostPasswordReminder.class);
		sender.remindLostPassword(u);
		EasyMock.replay(sender);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setLostPasswordReminder(sender);

		svc.sendLostPassword(email);

		// make sure an email was sent
		EasyMock.verify(sender);
	}

	public void testSendLostPasswordUnknownUser() {
		final String email = "user@gb.com";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		try {
			svc.sendLostPassword(email);
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	public void testActivateUserOk() throws Exception {
		final String email = "user@gb.com";
		final Token rightToken = new Token("123");

		ExposedUser user = new ExposedUser();
		user.setEmail(email);
		user.activationRequested(new UserActivationRequest(user, rightToken));

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(user);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		User activatedUser = svc.activateUser(email, rightToken);
		assertTrue(activatedUser.isActive());
	}

	public void testActivateUnknownUser() throws UserActivationException {
		final String email = "user@gb.com";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		try {
			svc.activateUser(email, new Token("123"));
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	public void testGetAllUsers() {
		Set<User> allUsers = Users.all();

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsers()).andReturn(allUsers);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		assertTrue(CollectionUtils.isEqualCollection(allUsers, svc.getAllUsers()));
	}

	public void testDeleteUsers() {
		Set<Long> userIds = new HashSet<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		userRepo.deleteUsers(userIds);
		EasyMock.expectLastCall();
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.deleteUsers(userIds);

		EasyMock.verify(userRepo);
	}

	public void testChangeUserPassword() throws Exception {
		ExposedUser user = new ExposedUser();
		user.setId(42L);
		user.setPassword("1");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(user);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.changeUserPassword(user.getId(), "1", "2");

		assertEquals("2", user.getPassword());
	}

	public void testResetUserPassword() {
		String newPwd = "12345678";
		int length = 8;

		PasswordGenerator pwdGenerator = EasyMock.createMock(PasswordGenerator.class);
		EasyMock.expect(pwdGenerator.generatePassword(length)).andReturn(newPwd);
		EasyMock.replay(pwdGenerator);

		Map<String, String> constants = new HashMap<String, String>();
		constants.put("user.generatedPasswordLength", "8");

		ExposedUser user = new ExposedUser();
		user.setId(42L);
		user.setPassword("old");
		user.setGlobalConstants(constants);
		user.setPasswordGenerator(pwdGenerator);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(user);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.resetUserPassword(user.getId());

		assertEquals(newPwd, user.getPassword());
	}
}
