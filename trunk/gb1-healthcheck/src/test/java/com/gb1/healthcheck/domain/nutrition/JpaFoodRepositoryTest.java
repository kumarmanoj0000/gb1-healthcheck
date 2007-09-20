package com.gb1.healthcheck.domain.nutrition;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;

public class JpaFoodRepositoryTest extends BaseRepositoryTestCase {
	private FoodRepository foodRepo;

	public void testLoadFood() {
//		final Long foodId = 1L;
//		assertEquals(foodId, foodRepo.loadFood(foodId).getId());
	}

	public void testFindSimpleFoodByName() {
//		final String name = "water";
//		assertEquals(name, foodRepo.findFoodByName(name).getName());
	}

	public void testFindComplexFoodByName() {
//		final String name = "spaghetti";
//		Food spag = foodRepo.findFoodByName(name);
//
//		// test loading of all dependencies
//		assertTrue(spag.isPartOfGroup(Group.FRUITS));
//		assertTrue(spag.isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
