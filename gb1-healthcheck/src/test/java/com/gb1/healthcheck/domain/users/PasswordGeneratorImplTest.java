package com.gb1.healthcheck.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class PasswordGeneratorImplTest {
	@Test
	public void testGeneratePassword() {
		final int length = 8;
		PasswordGeneratorImpl pg = new PasswordGeneratorImpl();
		String pwd = pg.generatePassword(length);

		assertEquals(length, pwd.length());
		assertTrue(StringUtils.isNotBlank(pwd));
	}
}
