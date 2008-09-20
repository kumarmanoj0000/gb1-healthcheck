package com.gb1.commons.tokens;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UuidActivationTokenFactoryTest {
	@Test
	public void testNewActivationTokenOk() {
		UuidTokenFactory factory = new UuidTokenFactory();
		Token token = factory.newToken();
		assertNotNull(token.getValue());
	}
}
