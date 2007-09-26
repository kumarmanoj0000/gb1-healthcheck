package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.Nutrient;

import junit.framework.TestCase;

public class SimpleFoodCreationRequestTest extends TestCase {
	public void testSetSelectedNutrients() {
		SimpleFoodCreationRequest req = new SimpleFoodCreationRequest();

		req.setSelectedNutrients(new String[] {});
		assertEquals(0, req.getSelectedNutrients().length);

		req.setSelectedNutrients(new String[] { "VITAMIN_A", "VITAMIN_B" });
		assertEquals(2, req.getSelectedNutrients().length);
		assertEquals(Nutrient.VITAMIN_A.name(), req.getSelectedNutrients()[0]);
		assertEquals(Nutrient.VITAMIN_B.name(), req.getSelectedNutrients()[1]);
	}
}
