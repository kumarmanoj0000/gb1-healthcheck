package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;

public class FullUserCreationValidatorTest extends TestCase {
	public void testValidateOk() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(null);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(null);
		EasyMock.replay(userRepo);

		FullUserCreationValidator v = new FullUserCreationValidator();
		v.setUserRepository(userRepo);
		v.validate(user);
	}

	public void testValidateLoginTaken() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(new User());
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(null);
		EasyMock.replay(userRepo);

		FullUserCreationValidator v = new FullUserCreationValidator();
		v.setUserRepository(userRepo);

		try {
			v.validate(user);
			fail("Login was already taken");
		}
		catch (LoginAlreadyExistsException e) {
			// ok
		}
	}

	public void testValidateEmailTaken() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(null);
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(new User());
		EasyMock.replay(userRepo);

		FullUserCreationValidator v = new FullUserCreationValidator();
		v.setUserRepository(userRepo);

		try {
			v.validate(user);
			fail("Email was already taken");
		}
		catch (EmailAlreadyExistsException e) {
			// ok
		}
	}

	private User buildValidUser() {
		ExposedUser user = new ExposedUser();
		user.setLogin("login");
		user.setEmail("email@com.com");
		user.setPassword("pass");

		return user;
	}
}
