package com.gb1.healthcheck.services.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.gb1.healthcheck.core.Token;
import com.gb1.healthcheck.core.Validator;
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
import com.gb1.healthcheck.domain.users.Users;

public class UserServiceImplTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testRegisterUser() throws UserException {
		User user = new User("login", "1");

		Validator<User, UserException> validator = EasyMock.createMock(Validator.class);
		validator.validate(EasyMock.isA(User.class));
		EasyMock.replay(validator);

		UserActivationRequest expectedActRequest = new UserActivationRequest(user, null);

		UserActivationRequester requester = EasyMock.createMock(UserActivationRequester.class);
		EasyMock.expect(requester.requestUserActivation(EasyMock.isA(User.class))).andReturn(
				expectedActRequest);
		EasyMock.replay(requester);

		UserRepository repo = EasyMock.createMock(UserRepository.class);
		repo.persist(EasyMock.isA(User.class));
		EasyMock.replay(repo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = repo;
		svc.userActivationRequester = requester;
		svc.userCreationValidator = validator;

		UserActivationRequest actRequest = svc.registerUser(user);

		// make sure that the user was validated and saved, and that the activation request was sent
		assertEquals(expectedActRequest, actRequest);
		EasyMock.verify(validator);
		EasyMock.verify(repo);
		EasyMock.verify(requester);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testModifyUserOk() throws UserException {
		final User user = Users.gb();

		Validator<User, UserException> validator = EasyMock.createMock(Validator.class);
		validator.validate(user);
		EasyMock.replay(validator);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		userRepo.merge(user);
		EasyMock.expectLastCall().once();
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userUpdateValidator = validator;
		svc.userRepository = userRepo;
		svc.updateUser(user);

		EasyMock.verify(validator);
		EasyMock.verify(userRepo);
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
		EasyMock.expect(userRepo.findAllUsers()).andReturn(allUsers);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		assertTrue(CollectionUtils.isEqualCollection(allUsers, svc.findAllUsers()));
	}

	@Test
	public void testDeleteUsers() {
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUser(Users.gb().getId())).andReturn(Users.gb());
		userRepo.delete(Users.gb());
		EasyMock.expectLastCall();
		EasyMock.expect(userRepo.findUser(Users.lg().getId())).andReturn(Users.lg());
		userRepo.delete(Users.lg());
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
		EasyMock.expect(userRepo.findUser(user.getId())).andReturn(user);
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
		EasyMock.expect(userRepo.findUser(user.getId())).andReturn(user);
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

	@Test
	public void testLoadUserByUsernameUnknown() {
		final String login = "user";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(login)).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		try {
			svc.loadUserByUsername(login);
			fail("Username was unknown");
		}
		catch (UsernameNotFoundException e) {
			// ok
		}
	}

	@Test
	public void testLoadUserByUsernameOk() {
		final String login = "user";
		ExposedUser u = new ExposedUser();
		u.setLogin(login);
		u.assignRole(Role.STANDARD);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(login)).andReturn(u);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.userRepository = userRepo;

		UserDetails details = svc.loadUserByUsername(login);
		assertEquals(details.getUsername(), u.getLogin());
		assertEquals(1, details.getAuthorities().length);
		assertEquals(details.getAuthorities()[0].getAuthority(), Role.STANDARD.getName());
	}
}
