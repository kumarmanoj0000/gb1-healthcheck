package com.gb1.healthcheck.domain.users;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

public class FullUserUpdateValidatorTest {
	@Test
	public void testValidateOk() throws UserException {
		ExposedUser user = new ExposedUser();
		user.setId(1L);
		user.setPassword("pass");
		user.setEmail("user@gb.com");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsersByEmail(user.getEmail())).andReturn(
				new LinkedList<User>());
		EasyMock.replay(userRepo);

		FullUserUpdateValidator validator = new FullUserUpdateValidator();
		validator.setUserRepository(userRepo);

		validator.validate(user);
	}

	@Test
	public void testValidateEmailExistsSameUser() throws UserException {
		ExposedUser user = new ExposedUser();
		user.setId(1L);
		user.setPassword("pass");
		user.setEmail("user@gb.com");

		List<User> usersWithSameEmail = new LinkedList<User>();
		usersWithSameEmail.add(user);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsersByEmail(user.getEmail())).andReturn(usersWithSameEmail);
		EasyMock.replay(userRepo);

		FullUserUpdateValidator validator = new FullUserUpdateValidator();
		validator.setUserRepository(userRepo);

		validator.validate(user);
	}

	@Test
	public void testValidateEmailExistsOtherUser() throws UserException {
		ExposedUser verifiedUser = new ExposedUser();
		verifiedUser.setId(1L);
		verifiedUser.setLogin("u1");
		verifiedUser.setPassword("pass");
		verifiedUser.setEmail("user@gb.com");

		ExposedUser otherUser = new ExposedUser();
		otherUser.setId(2L);
		otherUser.setLogin("u2");
		otherUser.setEmail(verifiedUser.getEmail());

		List<User> usersWithSameEmail = new LinkedList<User>();
		usersWithSameEmail.add(otherUser);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsersByEmail(verifiedUser.getEmail())).andReturn(
				usersWithSameEmail);
		EasyMock.replay(userRepo);

		FullUserUpdateValidator validator = new FullUserUpdateValidator();
		validator.setUserRepository(userRepo);

		try {
			validator.validate(verifiedUser);
			fail("Email was already owned by another other");
		}
		catch (EmailAlreadyExistsException e) {
			// ok
		}
	}
}
