package com.gb1.healthcheck.domain.foods;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

public class FullComplexFoodUpdateValidatorTest {
	@Test
	public void testValidate() throws FoodException {
		final ComplexFood food = Foods.spaghetti();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.foodRepo = foodRepo;

		v.validate(food);
	}

	@Test
	public void testValidateNameAlreadyTakenSameFood() throws FoodException {
		final ComplexFood food = Foods.spaghetti();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.foodRepo = foodRepo;

		v.validate(food);
	}

	@Test
	public void testValidateNameAlreadyTakenOtherFood() throws FoodException {
		final ComplexFood food = Foods.spaghetti();
		food.setId(-1L);

		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);
		foodsWithSameName.add(Foods.spaghetti());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.foodRepo = foodRepo;

		try {
			v.validate(food);
			fail("Food with same name already existed");
		}
		catch (FoodAlreadyExistsException expected) {
		}
	}

	@Test
	public void testValidateNoIngredients() throws FoodException {
		final ComplexFood food = new ComplexFood();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.foodRepo = foodRepo;

		try {
			v.validate(food);
			fail("Food had no ingredients");
		}
		catch (ComplexFoodHasNoIngredientsException expected) {
		}
	}
}
