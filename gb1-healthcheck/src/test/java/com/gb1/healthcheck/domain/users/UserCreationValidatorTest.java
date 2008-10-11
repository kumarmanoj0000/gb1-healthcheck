package com.gb1.healthcheck.domain.users;

import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.Test;

public class UserCreationValidatorTest {
	@Test
	public void testValidateOk() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(null);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(null);
		EasyMock.replay(userRepo);

		UserCreationValidator v = new UserCreationValidator();
		v.userRepository = userRepo;
		v.validate(user);
	}

	@Test
	public void testValidateLoginTaken() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(new User());
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(null);
		EasyMock.replay(userRepo);

		UserCreationValidator v = new UserCreationValidator();
		v.userRepository = userRepo;

		try {
			v.validate(user);
			fail("Login was already taken");
		}
		catch (LoginAlreadyExistsException e) {
			// ok
		}
	}

	@Test
	public void testValidateEmailTaken() throws UserException {
		User user = buildValidUser();

		// mock returning null user
		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(user.getLogin())).andReturn(null);
		EasyMock.expect(userRepo.findUserByEmail(user.getEmail())).andReturn(new User());
		EasyMock.replay(userRepo);

		UserCreationValidator v = new UserCreationValidator();
		v.userRepository = userRepo;

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
