package com.gb1.healthcheck.domain.foods;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

public class FullComplexFoodUpdateValidatorTest extends TestCase {
	public void testValidate() throws FoodException {
		final ComplexFood food = Foods.spaghetti();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.setFoodRepository(foodRepo);

		v.validate(food);
	}

	public void testValidateNameAlreadyTakenSameFood() throws FoodException {
		final ComplexFood food = Foods.spaghetti();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.setFoodRepository(foodRepo);

		v.validate(food);
	}

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
		v.setFoodRepository(foodRepo);

		try {
			v.validate(food);
			fail("Food with same name already existed");
		}
		catch (FoodAlreadyExistsException expected) {
		}
	}

	public void testValidateNoIngredients() throws FoodException {
		final ComplexFood food = new ComplexFood("no ingredients");
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodUpdateValidator v = new FullComplexFoodUpdateValidator();
		v.setFoodRepository(foodRepo);

		try {
			v.validate(food);
			fail("Food had no ingredients");
		}
		catch (ComplexFoodHasNoIngredientsException expected) {
		}
	}
}
