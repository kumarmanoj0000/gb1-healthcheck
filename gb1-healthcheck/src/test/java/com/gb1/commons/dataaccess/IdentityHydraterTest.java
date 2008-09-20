package com.gb1.commons.dataaccess;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class IdentityHydraterTest {
	@Test
	public void testHydrate() {
		final String toHydrate = "test";
		IdentityHydrater<String> h = new IdentityHydrater<String>();
		assertSame(toHydrate, h.hydrate(toHydrate));
	}
}
