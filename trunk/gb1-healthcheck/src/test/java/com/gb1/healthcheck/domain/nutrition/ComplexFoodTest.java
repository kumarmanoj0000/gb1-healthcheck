package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

public class ComplexFoodTest extends TestCase {
	public void testNewFromProvider() {
		ComplexFoodPropertyProvider provider = new ComplexFoodPropertyProvider() {
			public String getName() {
				return Foods.spaghetti().getName();
			}

			public Set<Food> getIngredients() {
				return Foods.spaghetti().getIngredients();
			}
		};

		ComplexFood food = new ComplexFood(provider);
		assertEquals(Foods.spaghetti(), food);
		assertTrue(CollectionUtils.isEqualCollection(Foods.spaghetti().getIngredients(), food.getIngredients()));
	}

	public void testGetNutrients() {
		assertTrue(Foods.redWine().containsIngredient(Foods.redGrape()));
		assertTrue(Foods.redWine().isSourceOfNutrient(Nutrient.ALCOHOL));

		assertTrue(Foods.spaghetti().containsIngredient(Foods.water()));
		assertTrue(Foods.spaghetti().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testIsPartOfGroup() {
		assertTrue(Foods.spaghetti().isPartOfGroup(Group.FRUITS));
		assertTrue(Foods.spaghetti().isPartOfGroup(Group.MEAT_AND_SUBSTITUTES));
	}
}
