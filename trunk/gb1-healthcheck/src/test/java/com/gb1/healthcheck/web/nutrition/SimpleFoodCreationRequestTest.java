package com.gb1.healthcheck.web.nutrition;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;

import com.gb1.healthcheck.domain.nutrition.Nutrient;

public class SimpleFoodCreationRequestTest extends TestCase {
	public void testSetSelectedNutrients() {
		SimpleFoodCreationRequest req = new SimpleFoodCreationRequest();

		req.setSelectedNutrients(new String[] {});
		assertEquals(0, req.getSelectedNutrients().length);

		req.setSelectedNutrients(new String[] { Nutrient.VITAMIN_A.name(),
				Nutrient.VITAMIN_B.name() });
		assertEquals(2, req.getSelectedNutrients().length);
		assertTrue(ArrayUtils.contains(req.getSelectedNutrients(), Nutrient.VITAMIN_A.name()));
		assertTrue(ArrayUtils.contains(req.getSelectedNutrients(), Nutrient.VITAMIN_B.name()));
	}
}
