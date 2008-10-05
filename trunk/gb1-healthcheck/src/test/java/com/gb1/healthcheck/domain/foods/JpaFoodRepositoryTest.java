package com.gb1.healthcheck.domain.foods;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.gb1.test.AbstractInMemoryPersistenceTestCase;

public class JpaFoodRepositoryTest extends AbstractInMemoryPersistenceTestCase {
	@Resource
	private FoodRepository foodRepo;

	@Test
	public void testLoadFood() {
		final Long foodId = 1L;
		assertEquals(foodId, foodRepo.loadFood(foodId).getId());
	}

	@Test
	public void testFindSimpleFoodByName() {
		final String name = "water";
		assertEquals(name, foodRepo.findFoodByName(name).getName());
	}

	@Test
	public void testFindComplexFoodByName() {
		final String name = "spaghetti";
		Food spag = foodRepo.findFoodByName(name);

		// test loading of all dependencies
		assertTrue(spag.isPartOfFoodGroup(FoodGroup.FRUITS));
		assertTrue(spag.isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	@Test
	public void testFindSimpleFoods() {
		Set<SimpleFood> expectedFoods = Foods.allSimpleFoods();
		List<SimpleFood> loadedFoods = foodRepo.findSimpleFoods();
		assertTrue(CollectionUtils.isEqualCollection(expectedFoods, loadedFoods));
	}

	@Test
	public void testFindComplexFoods() {
		Set<ComplexFood> expectedFoods = Foods.allComplexFoods();
		List<ComplexFood> loadedFoods = foodRepo.findComplexFoods();
		assertTrue(CollectionUtils.isEqualCollection(expectedFoods, loadedFoods));
	}

	@Test
	public void testDeleteFood() {
		Food food = foodRepo.loadFood(Foods.apple().getId());
		foodRepo.delete(food);
		assertNull(foodRepo.loadFood(food.getId()));
	}
}
