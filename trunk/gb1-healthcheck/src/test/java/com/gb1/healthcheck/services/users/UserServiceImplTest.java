package com.gb1.healthcheck.services.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.PasswordGenerator;
import com.gb1.healthcheck.domain.users.PasswordResetNotifier;
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

public class UserServiceImplTest {
	@Test
	public void testRegisterUser() throws UserException {
		UserRegistrationRequest request = new UserRegistrationRequest() {
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

		User expectedUser = new User(request.getLogin(), request.getEmail(), request.getPassword());
		UserActivationRequest expectedActRequest = new UserActivationRequest(expectedUser, null);

		UserActivationRequester requester = EasyMock.createMock(UserActivationRequester.class);
		EasyMock.expect(requester.requestUserActivation(EasyMock.isA(User.class))).andReturn(
				expectedActRequest);
		EasyMock.replay(requester);

		UserRepository repo = EasyMock.createMock(UserRepository.class);
		repo.saveUser(EasyMock.isA(User.class));
		EasyMock.replay(repo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userAssembler = new UserAssemblerImpl();
		svc.userRepository = repo;
		svc.userActivationRequester = requester;
		svc.userCreationValidator = validator;

		UserActivationRequest actRequest = svc.registerUser(request);

		// make sure that the user was validated and saved, and that the activation request was sent
		assertEquals(expectedActRequest, actRequest);
		EasyMock.verify(validator);
		EasyMock.verify(repo);
		EasyMock.verify(requester);
	}

	@Test
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
		svc.userAssembler = new UserAssemblerImpl();
		svc.userRepository = userRepo;

		try {
			svc.updateUser(modifReq);
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	@Test
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
		svc.userAssembler = new UserAssemblerImpl();
		svc.userRepository = userRepo;
		svc.userUpdateValidator = validator;

		User modifiedUser = svc.updateUser(updateRequest);

		assertNotNull(modifiedUser);
		assertEquals(modifiedUser, originalUser);
		assertEquals(modifiedUser.getLogin(), originalUser.getLogin());
		assertEquals(modifiedUser.getEmail(), updateRequest.getEmail());
	}

	@Test
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
		svc.userRepository = userRepo;
		svc.lostPasswordReminder = sender;

		svc.sendLostPassword(email);

		// make sure an email was sent
		EasyMock.verify(sender);
	}

	@Test
	public void testSendLostPasswordUnknownUser() {
		final String email = "user@gb.com";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		try {
			svc.sendLostPassword(email);
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	@Test
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
		svc.userRepository = userRepo;

		User activatedUser = svc.activateUser(email, rightToken);
		assertTrue(activatedUser.isActive());
	}

	@Test
	public void testActivateUnknownUser() throws UserActivationException {
		final String email = "user@gb.com";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(email)).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		try {
			svc.activateUser(email, new Token("123"));
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	@Test
	public void testGetAllUsers() {
		List<User> allUsers = new ArrayList<User>(Users.all());

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsers()).andReturn(allUsers);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		assertTrue(CollectionUtils.isEqualCollection(allUsers, svc.getAllUsers()));
	}

	@Test
	public void testDeleteUsers() {
		Set<Long> userIds = new LinkedHashSet<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(Users.gb().getId())).andReturn(Users.gb());
		userRepo.deleteUser(Users.gb());
		EasyMock.expectLastCall();
		EasyMock.expect(userRepo.loadUser(Users.lg().getId())).andReturn(Users.lg());
		userRepo.deleteUser(Users.lg());
		EasyMock.expectLastCall();
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;
		svc.deleteUsers(userIds);

		EasyMock.verify(userRepo);
	}

	@Test
	public void testChangeUserPassword() throws Exception {
		ExposedUser user = new ExposedUser();
		user.setId(42L);
		user.setPassword("1");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(user);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;
		svc.changeUserPassword(user.getId(), "1", "2");

		assertEquals("2", user.getPassword());
	}

	@Test
	public void testResetUserPassword() {
		String newPwd = "12345678";
		int length = 8;

		ExposedUser user = new ExposedUser();
		user.setId(42L);
		user.setPassword("old");

		PasswordGenerator pwdGenerator = EasyMock.createMock(PasswordGenerator.class);
		EasyMock.expect(pwdGenerator.generatePassword(length)).andReturn(newPwd);
		EasyMock.replay(pwdGenerator);

		Map<String, String> constants = new HashMap<String, String>();
		constants.put("user.generatedPasswordLength", "8");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(user);
		EasyMock.replay(userRepo);

		PasswordResetNotifier notifier = EasyMock.createMock(PasswordResetNotifier.class);
		notifier.notifyPasswordReset(user);
		EasyMock.expectLastCall().once();
		EasyMock.replay(notifier);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;
		svc.passwordGenerator = pwdGenerator;
		svc.passwordResetNotifier = notifier;
		svc.setGlobalConstants(constants);
		svc.resetUserPassword(user.getId());

		assertEquals(newPwd, user.getPassword());
		EasyMock.verify(notifier);
	}
}
