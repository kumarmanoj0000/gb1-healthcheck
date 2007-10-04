package com.gb1.healthcheck.domain.nutrition;

import java.util.Collections;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

public class SimpleFoodTest extends TestCase {
	public void testIsPartOfGroup() {
		assertTrue(Foods.apple().isPartOfGroup(Group.FRUITS));
		assertFalse(Foods.apple().isPartOfGroup(Group.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		assertTrue(Foods.apple().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testNewSimpleFoodUsingPropertyProvider() {
		final SimpleFood oldFood = Foods.apple();
		SimpleFoodPropertyProvider request = new SimpleFoodPropertyProvider() {
			public String getName() {
				return oldFood.getName();
			}

			public Group getGroup() {
				return oldFood.getGroup();
			}

			public Set<Nutrient> getNutrients() {
				return oldFood.getNutrients();
			}
		};

		SimpleFood food = new SimpleFood(request);
		assertEquals(oldFood.getName(), food.getName());
		assertEquals(oldFood.getGroup(), food.getGroup());
		assertTrue(CollectionUtils.isEqualCollection(oldFood.getNutrients(), food.getNutrients()));
	}

	public void testUpdateUsingPropertyProvider() {
		final SimpleFood oldFood = Foods.apple();
		SimpleFoodMutablePropertyProvider request = new SimpleFoodMutablePropertyProvider() {
			public Group getGroup() {
				return oldFood.getGroup();
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
		assertEquals(oldFood.getGroup(), food.getGroup());
		assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Nutrient.VITAMIN_C),
				food.getNutrients()));
	}
}
