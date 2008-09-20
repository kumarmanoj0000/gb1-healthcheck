package com.gb1.healthcheck.domain.users;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.gb1.commons.test.AbstractInMemoryPersistenceTestCase;

public class JpaUserRepositoryTest extends AbstractInMemoryPersistenceTestCase {
	@Resource
	private UserRepository userRepo = null;

	@Test
	public void testLoadUser() {
		final Long userId = 1L;
		User u = userRepo.loadUser(userId);

		assertEquals(userId, u.getId());
	}

	@Test
	public void testLoadUnknownUser() {
		assertNull(userRepo.loadUser(-1L));
	}

	@Test
	public void testFindUsers() {
		assertEquals(52, userRepo.findUsers().size());
	}

	@Test
	public void testFindUserByLogin() {
		final String login = "gbilodeau";
		assertEquals(login, userRepo.findUserByLogin(login).getLogin());
	}

	@Test
	public void testFindUserByUnknownLogin() {
		assertNull(userRepo.findUserByLogin("unknown"));
	}

	@Test
	public void testFindUserByEmail() {
		final String email = "gbilodeau@yahoo.com";
		assertEquals(email, userRepo.findUserByEmail(email).getEmail());
	}

	@Test
	public void testFindUserByUnknownEmail() {
		assertNull(userRepo.findUserByEmail("unknown"));
	}

	@Test
	public void testSaveUser() {
		User u = new User("login", "email", "1");
		u.assignRole(Role.ADMINISTRATOR);

		userRepo.saveUser(u);
		assertEquals(u, userRepo.loadUser(u.getId()));
	}

	@Test
	public void testDeleteUsers() {
		Set<Long> userIds = new HashSet<Long>();
		userIds.add(Users.gb().getId());
		userIds.add(Users.lg().getId());

		userRepo.deleteUsers(userIds);

		assertNull(userRepo.loadUser(Users.gb().getId()));
		assertNull(userRepo.loadUser(Users.lg().getId()));
	}
}
