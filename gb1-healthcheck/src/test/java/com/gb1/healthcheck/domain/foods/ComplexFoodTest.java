package com.gb1.healthcheck.domain.foods;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

public class ComplexFoodTest extends TestCase {
	public void testNewFromProvider() {
		ComplexFoodCreationPropertyProvider provider = new ComplexFoodCreationPropertyProvider() {
			public String getName() {
				return Foods.spaghetti().getName();
			}

			public Set<Food> getIngredients() {
				return Foods.spaghetti().getIngredients();
			}
		};

		ComplexFood food = new ComplexFood(provider);
		assertEquals(Foods.spaghetti(), food);
		assertTrue(CollectionUtils.isEqualCollection(Foods.spaghetti().getIngredients(), food
				.getIngredients()));
	}

	public void testGetNutrients() {
		assertTrue(Foods.redWine().containsIngredient(Foods.redGrape()));
		assertTrue(Foods.redWine().isSourceOfNutrient(Nutrient.ALCOHOL));

		assertTrue(Foods.spaghetti().containsIngredient(Foods.water()));
		assertTrue(Foods.spaghetti().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testIsPartOfGroup() {
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	public void testUpdate() {
		// no more beef & beef broth!
		final String updatedName = "vegetarian spaghetti";
		final Set<Food> updatedIngredients = new HashSet<Food>();
		updatedIngredients.add(Foods.tomato());
		updatedIngredients.add(Foods.pasta());

		ComplexFoodUpdatePropertyProvider provider = new ComplexFoodUpdatePropertyProvider() {
			public Set<Food> getIngredients() {
				return updatedIngredients;
			}

			public String getName() {
				return updatedName;
			}
		};

		ComplexFood spaghetti = Foods.spaghetti();
		spaghetti.update(provider);

		assertEquals(updatedName, spaghetti.getName());
		assertTrue(CollectionUtils
				.isEqualCollection(updatedIngredients, spaghetti.getIngredients()));
	}
}
