package com.gb1.healthcheck.web.foods;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.foods.Foods;

public class BasicSimpleFoodUpdateRequestTest extends TestCase {
	public void testNewRequestFromFood() {
		BasicSimpleFoodUpdateRequest req = new BasicSimpleFoodUpdateRequest(Foods.apple());
		assertEquals(Foods.apple().getName(), req.getName());
		assertEquals(Foods.apple().getFoodGroup(), req.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(Foods.apple().getNutrients(), req
				.getNutrients()));
	}
}
