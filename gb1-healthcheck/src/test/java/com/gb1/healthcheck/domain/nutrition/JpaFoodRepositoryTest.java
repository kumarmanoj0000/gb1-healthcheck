package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;

public class JpaFoodRepositoryTest extends BaseRepositoryTestCase {
	private FoodRepository foodRepo;

	public void testLoadFood() {
		final Long foodId = 1L;
		assertEquals(foodId, foodRepo.loadFood(foodId).getId());
	}

	public void testFindSimpleFoodByName() {
		final String name = "water";
		assertEquals(name, foodRepo.findFoodByName(name).getName());
	}

	public void testFindComplexFoodByName() {
		final String name = "spaghetti";
		Food spag = foodRepo.findFoodByName(name);

		// test loading of all dependencies
		assertTrue(spag.isPartOfGroup(Group.FRUITS));
		assertTrue(spag.isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testFindSimpleFoods() {
		Set<SimpleFood> expectedFoods = Foods.allSimpleFoods();
		Set<SimpleFood> loadedFoods = foodRepo.findSimpleFoods();
		assertTrue(CollectionUtils.isEqualCollection(expectedFoods, loadedFoods));
	}

	public void testFindComplexFoods() {
		Set<ComplexFood> expectedFoods = Foods.allComplexFoods();
		Set<ComplexFood> loadedFoods = foodRepo.findComplexFoods();
		assertTrue(CollectionUtils.isEqualCollection(expectedFoods, loadedFoods));
	}

	public void testDeleteFood() {
		final long foodId = 1L;
		foodRepo.deleteFood(foodId);
		assertNull(foodRepo.loadFood(foodId));
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
