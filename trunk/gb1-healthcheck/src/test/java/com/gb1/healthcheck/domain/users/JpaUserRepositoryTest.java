package com.gb1.healthcheck.domain.users;

import java.util.Collections;
import java.util.Set;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;

public class JpaUserRepositoryTest extends BaseRepositoryTestCase {
	private UserRepository userRepo = null;

	public void testLoadUser() {
		final Long userId = 1L;
		User u = userRepo.loadUser(userId);

		assertEquals(userId, u.getId());
	}

	public void testLoadUnknownUser() {
		assertNull(userRepo.loadUser(-1L));
	}

	public void testFindUsers() {
		assertEquals(52, userRepo.findUsers().size());
	}

	public void testFindUserByLogin() {
		final String login = "gbilodeau";
		assertEquals(login, userRepo.findUserByLogin(login).getLogin());
	}

	public void testFindUserByUnknownLogin() {
		assertNull(userRepo.findUserByLogin("unknown"));
	}

	public void testFindUserByEmail() {
		final String email = "gbilodeau@yahoo.com";
		assertEquals(email, userRepo.findUserByEmail(email).getEmail());
	}

	public void testFindUserByUnknownEmail() {
		assertNull(userRepo.findUserByEmail("unknown"));
	}

	public void testSaveUser() {
		User u = new User(new UserPropertyProvider() {
			public String getLogin() {
				return "login";
			}

			public String getPassword() {
				return "pwd";
			}

			public String getEmail() {
				return "email";
			}

			public Set<Role> getRoles() {
				return Collections.singleton(Role.ADMINISTRATOR);
			}
		});

		userRepo.saveUser(u);
		assertEquals(u, userRepo.loadUser(u.getId()));
	}

	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
}
