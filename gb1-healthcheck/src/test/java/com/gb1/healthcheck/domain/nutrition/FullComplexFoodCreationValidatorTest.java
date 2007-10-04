package com.gb1.healthcheck.domain.nutrition;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

public class FullComplexFoodCreationValidatorTest extends TestCase {
	public void testValidate() throws FoodException {
		final ComplexFood food = Foods.spaghetti();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(new ArrayList<Food>());
		EasyMock.replay(foodRepo);

		FullComplexFoodCreationValidator v = new FullComplexFoodCreationValidator();
		v.setFoodRepository(foodRepo);

		v.validate(food);
	}

	public void testValidateNameAlreadyTaken() throws FoodException {
		final ComplexFood food = Foods.spaghetti();

		List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullComplexFoodCreationValidator v = new FullComplexFoodCreationValidator();
		v.setFoodRepository(foodRepo);

		try {
			v.validate(food);
			fail("Name was already taken");
		}
		catch (FoodAlreadyExistsException expected) {
		}
	}

	public void testValidateNoIngredients() throws FoodException {
		final ComplexFood food = new ComplexFood("no ingredients");

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(new ArrayList<Food>());
		EasyMock.replay(foodRepo);

		FullComplexFoodCreationValidator v = new FullComplexFoodCreationValidator();
		v.setFoodRepository(foodRepo);

		try {
			v.validate(food);
			fail("Food had no ingredients");
		}
		catch (ComplexFoodHasNoIngredientsException expected) {
		}
	}
}
