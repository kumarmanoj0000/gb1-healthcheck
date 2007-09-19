package com.gb1.healthcheck.domain.nutrition;

import junit.framework.TestCase;

public class SimpleFoodTest extends TestCase {
	public void testIsPartOfGroup() {
		assertTrue(Foods.apple().isPartOfGroup(Group.FRUITS));
		assertFalse(Foods.apple().isPartOfGroup(Group.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		assertTrue(Foods.apple().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}
}
