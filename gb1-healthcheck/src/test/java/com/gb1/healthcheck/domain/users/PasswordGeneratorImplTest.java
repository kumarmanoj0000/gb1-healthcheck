package com.gb1.healthcheck.domain.users;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

public class PasswordGeneratorImplTest extends TestCase {
	public void testGeneratePassword() {
		final int length = 8;
		PasswordGeneratorImpl pg = new PasswordGeneratorImpl();
		String pwd = pg.generatePassword(length);

		assertEquals(length, pwd.length());
		assertTrue(StringUtils.isNotBlank(pwd));
	}
}
