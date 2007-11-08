package com.gb1.healthcheck.domain.foods;

import java.util.Collections;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

public class SimpleFoodTest extends TestCase {
	public void testIsPartOfGroup() {
		assertTrue(Foods.apple().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertFalse(Foods.apple().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		assertTrue(Foods.apple().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testNewSimpleFoodUsingPropertyProvider() {
		final SimpleFood oldFood = Foods.apple();
		SimpleFoodCreationPropertyProvider request = new SimpleFoodCreationPropertyProvider() {
			public String getName() {
				return oldFood.getName();
			}

			public FoodGroup getFoodGroup() {
				return oldFood.getFoodGroup();
			}

			public Set<Nutrient> getNutrients() {
				return oldFood.getNutrients();
			}
		};

		SimpleFood food = new SimpleFood(request);
		assertEquals(oldFood.getName(), food.getName());
		assertEquals(oldFood.getFoodGroup(), food.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(oldFood.getNutrients(), food.getNutrients()));
	}

	public void testUpdateUsingPropertyProvider() {
		final SimpleFood oldFood = Foods.apple();
		SimpleFoodUpdatePropertyProvider request = new SimpleFoodUpdatePropertyProvider() {
			public FoodGroup getFoodGroup() {
				return oldFood.getFoodGroup();
			}

			public String getName() {
				return "updated apple";
			}

			public Set<Nutrient> getNutrients() {
				return Collections.singleton(Nutrient.VITAMIN_C);
			}
		};

		SimpleFood food = new SimpleFood();
		food.update(request);

		assertEquals("updated apple", food.getName());
		assertEquals(oldFood.getFoodGroup(), food.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Nutrient.VITAMIN_C),
				food.getNutrients()));
	}
}
