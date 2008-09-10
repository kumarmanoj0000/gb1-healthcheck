package com.gb1.healthcheck.services.users;

import java.util.Collections;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.User;

public class UserAssemblerImplTest extends TestCase {
	public void testCreate() {
		UserRegistrationRequest request = new UserRegistrationRequest() {
			public String getEmail() {
				return "email";
			}

			public String getLogin() {
				return "login";
			}

			public String getPassword() {
				return "pwd";
			}

			public Set<Role> getRoles() {
				return Collections.singleton(Role.ADMINISTRATOR);
			}
		};

		UserAssemblerImpl assembler = new UserAssemblerImpl();
		User user = assembler.createUser(request);

		assertEquals(request.getLogin(), user.getLogin());
		assertEquals(request.getEmail(), user.getEmail());
		assertEquals(request.getPassword(), user.getPassword());
		assertTrue(CollectionUtils.isEqualCollection(request.getRoles(), user.getRoles()));
	}

	public void testUpdate() {
		final User user = new User("login", "email", "pwd");
		user.assignRole(Role.STANDARD);

		UserUpdateRequest request = new UserUpdateRequest() {
			public Long getUserId() {
				return user.getId();
			}

			public String getEmail() {
				return "newEmail";
			}

			public Set<Role> getRoles() {
				return Collections.singleton(Role.ADMINISTRATOR);
			}
		};

		UserAssemblerImpl assembler = new UserAssemblerImpl();
		assembler.updateMeal(user, request);

		assertEquals("login", user.getLogin());
		assertEquals(request.getEmail(), user.getEmail());
		assertTrue(user.hasRole(Role.ADMINISTRATOR));
		assertFalse(user.hasRole(Role.STANDARD));
	}
}
