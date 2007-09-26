package com.gb1.healthcheck.web.nutrition;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.nutrition.Foods;

public class SimpleFoodUpdateRequestTest extends TestCase {
	public void testNewRequestFromFood() {
		SimpleFoodUpdateRequest req = new SimpleFoodUpdateRequest(Foods.apple());
		assertEquals(Foods.apple().getName(), req.getName());
		assertEquals(Foods.apple().getGroup(), req.getGroup());
		assertTrue(CollectionUtils.isEqualCollection(Foods.apple().getNutrients(), req
				.getNutrients()));
	}
}
