package com.gb1.healthcheck.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UuidTokenFactoryTest {
	@Test
	public void testNewActivationTokenOk() {
		UuidTokenFactory factory = new UuidTokenFactory();
		Token token = factory.newToken();
		assertNotNull(token.getValue());
	}
}
