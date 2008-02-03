package com.gb1.healthcheck.services.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.LostPasswordReminder;
import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.UserActivationRequester;
import com.gb1.healthcheck.domain.users.UserException;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.UserValidator;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.web.users.BasicUserRegistrationRequest;
import com.gb1.healthcheck.web.users.BasicUserUpdateRequest;

public class UserServiceImplTest extends TestCase {
	public void testRegisterUser() throws UserException {
		BasicUserRegistrationRequest regRequest = new BasicUserRegistrationRequest();
		regRequest.setLogin("login");

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
		BasicUserUpdateRequest modifReq = new BasicUserUpdateRequest(user);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(user.getId())).andReturn(null);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		try {
			svc.updateUser(user.getId(), modifReq);
			fail("User was unknown");
		}
		catch (UnknownUserException e) {
			// ok
		}
	}

	public void testModifyUserOk() throws UserException {
		ExposedUser originalUser = new ExposedUser();
		originalUser.setId(1L);
		originalUser.setLogin("user");
		originalUser.setEmail("user@gb.com");
		originalUser.setPassword("pass");

		BasicUserUpdateRequest updateRequest = new BasicUserUpdateRequest(originalUser);

		UserValidator validator = EasyMock.createMock(UserValidator.class);
		validator.validate(originalUser);
		EasyMock.replay(validator);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(originalUser.getId())).andReturn(originalUser);
		EasyMock.replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setUserUpdateValidator(validator);

		User modifiedUser = svc.updateUser(originalUser.getId(), updateRequest);

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
		svc.setLostPasswordSender(sender);

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
}
