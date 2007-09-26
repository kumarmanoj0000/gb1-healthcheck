package com.gb1.healthcheck.domain.nutrition;

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
		SimpleFoodPropertyProvider request = new SimpleFoodPropertyProvider() {
			public String getName() {
				return Foods.apple().getName();
			}

			public Group getGroup() {
				return Foods.apple().getGroup();
			}

			public Set<Nutrient> getNutrients() {
				return Foods.apple().getNutrients();
			}
		};

		SimpleFood food = new SimpleFood(request);
		assertEquals(Foods.apple().getName(), food.getName());
		assertEquals(Foods.apple().getGroup(), food.getGroup());
		assertTrue(CollectionUtils.isEqualCollection(Foods.apple().getNutrients(), food
				.getNutrients()));
	}

	public void testUpdateUsingPropertyProvider() {
		SimpleFoodPropertyProvider request = new SimpleFoodPropertyProvider() {
			public Group getGroup() {
				return Foods.apple().getGroup();
			}

			public String getName() {
				return Foods.apple().getName();
			}

			public Set<Nutrient> getNutrients() {
				return Foods.apple().getNutrients();
			}
		};

		SimpleFood food = new SimpleFood();
		food.update(request);

		assertEquals(Foods.apple().getName(), food.getName());
		assertEquals(Foods.apple().getGroup(), food.getGroup());
		assertTrue(CollectionUtils.isEqualCollection(Foods.apple().getNutrients(), food
				.getNutrients()));
	}
}
