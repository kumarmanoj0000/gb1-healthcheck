package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.Nutrient;

public class SimpleFoodRequestSupportTest {
	@Test
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
