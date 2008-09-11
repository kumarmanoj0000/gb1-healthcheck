package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.Foods;

public class BasicSimpleFoodUpdateRequestTest {
	@Test
	public void testNewRequestFromFood() {
		BasicSimpleFoodUpdateRequest req = new BasicSimpleFoodUpdateRequest(Foods.apple());
		assertEquals(Foods.apple().getName(), req.getName());
		assertEquals(Foods.apple().getFoodGroup(), req.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(Foods.apple().getNutrients(), req
				.getNutrients()));
	}
}
