package com.gb1.healthcheck.services.users;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

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
import com.gb1.healthcheck.web.users.ExposedUserUpdateRequest;
import com.gb1.healthcheck.web.users.UserRegistrationRequest;
import com.gb1.healthcheck.web.users.UserUpdateRequest;

public class UserServiceImplTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		PlatformTransactionManager txManager = EasyMock
				.createMock(PlatformTransactionManager.class);
		AnnotationTransactionAspect.aspectOf().setTransactionManager(txManager);
	}

	public void testRegisterUser() throws UserException {
		UserRegistrationRequest regRequest = new UserRegistrationRequest();
		regRequest.setLogin("login");

		UserValidator validator = createMock(UserValidator.class);
		validator.validate(isA(User.class));
		replay(validator);

		User expectedUser = new ExposedUser(regRequest);
		UserActivationRequest expectedActRequest = new UserActivationRequest(expectedUser, null);

		UserActivationRequester requester = createMock(UserActivationRequester.class);
		expect(requester.requestUserActivation(isA(User.class))).andReturn(expectedActRequest);
		replay(requester);

		UserRepository repo = createMock(UserRepository.class);
		repo.saveUser(isA(User.class));
		replay(repo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(repo);
		svc.setUserActivationRequester(requester);
		svc.setUserCreationValidator(validator);

		UserActivationRequest actRequest = svc.registerUser(regRequest);

		// make sure that the user was validated and saved, and that the activation request was sent
		assertEquals(expectedActRequest, actRequest);
		verify(validator);
		verify(repo);
		verify(requester);
	}

	public void testModifyUnknownUser() throws UserException {
		ExposedUserUpdateRequest modifReq = new ExposedUserUpdateRequest();
		modifReq.setId(1L);

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.loadUser(modifReq.getId())).andReturn(null);
		replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		try {
			svc.updateUser(modifReq.getId(), modifReq);
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

		UserUpdateRequest updateRequest = new UserUpdateRequest(originalUser);

		UserValidator validator = createMock(UserValidator.class);
		validator.validate(originalUser);
		replay(validator);

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.loadUser(updateRequest.getId())).andReturn(originalUser);
		replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setUserUpdateValidator(validator);

		User modifiedUser = svc.updateUser(updateRequest.getId(), updateRequest);

		assertNotNull(modifiedUser);
		assertEquals(modifiedUser, originalUser);
		assertEquals(modifiedUser.getLogin(), originalUser.getLogin());
		assertEquals(modifiedUser.getEmail(), updateRequest.getEmail());
	}

	public void testSendLostPasswordOk() throws UnknownUserException {
		final String email = "user@gb.com";
		User u = new ExposedUser();
		u.setEmail(email);

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.findUserByEmail(email)).andReturn(u);
		replay(userRepo);

		LostPasswordReminder sender = createMock(LostPasswordReminder.class);
		sender.remindLostPassword(u);
		replay(sender);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setLostPasswordSender(sender);

		svc.sendLostPassword(email);

		// make sure an email was sent
		verify(sender);
	}

	public void testSendLostPasswordUnknownUser() {
		final String email = "user@gb.com";

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.findUserByEmail(email)).andReturn(null);
		replay(userRepo);

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

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.findUserByEmail(email)).andReturn(user);
		replay(userRepo);

		UserServiceImpl svc = new UserServiceImpl();
		svc.setUserRepository(userRepo);

		User activatedUser = svc.activateUser(email, rightToken);
		assertTrue(activatedUser.isActive());
	}

	public void testActivateUnknownUser() throws UserActivationException {
		final String email = "user@gb.com";

		UserRepository userRepo = createMock(UserRepository.class);
		expect(userRepo.findUserByEmail(email)).andReturn(null);
		replay(userRepo);

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
