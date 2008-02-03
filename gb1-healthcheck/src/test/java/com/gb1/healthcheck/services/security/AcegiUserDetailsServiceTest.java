package com.gb1.healthcheck.services.security;

import junit.framework.TestCase;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserRepository;

public class AcegiUserDetailsServiceTest extends TestCase {
	public void testLoadUserByUsernameUnknown() {
		final String login = "user";

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(login)).andReturn(null);
		EasyMock.replay(userRepo);

		AcegiUserDetailsService svc = new AcegiUserDetailsService();
		svc.setUserRepository(userRepo);

		try {
			svc.loadUserByUsername(login);
			fail("Username was unknown");
		}
		catch (UsernameNotFoundException e) {
			// ok
		}
	}

	public void testLoadUserByUsernameOk() {
		final String login = "user";
		ExposedUser u = new ExposedUser();
		u.setLogin(login);
		u.assignRole(Role.STANDARD);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUserByLogin(login)).andReturn(u);
		EasyMock.replay(userRepo);

		AcegiUserDetailsService svc = new AcegiUserDetailsService();
		svc.setUserRepository(userRepo);

		UserDetails details = svc.loadUserByUsername(login);
		assertEquals(details.getUsername(), u.getLogin());
		assertEquals(1, details.getAuthorities().length);
		assertEquals(details.getAuthorities()[0].getAuthority(), Role.STANDARD.getName());
	}
}
