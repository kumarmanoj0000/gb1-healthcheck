package com.gb1.healthcheck.web.foods;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;

import com.gb1.healthcheck.domain.foods.Nutrient;

public class SimpleFoodRequestSupportTest extends TestCase {
	public void testSetSelectedNutrients() {
		SimpleFoodRequestSupport req = new SimpleFoodRequestSupport() {
		};

		req.setSelectedNutrients(new String[] {});
		assertEquals(0, req.getSelectedNutrients().length);

		req.setSelectedNutrients(new String[] { Nutrient.VITAMIN_A.name(),
				Nutrient.VITAMIN_B.name() });
		assertEquals(2, req.getSelectedNutrients().length);
		assertTrue(ArrayUtils.contains(req.getSelectedNutrients(), Nutrient.VITAMIN_A.name()));
		assertTrue(ArrayUtils.contains(req.getSelectedNutrients(), Nutrient.VITAMIN_B.name()));
	}
}
