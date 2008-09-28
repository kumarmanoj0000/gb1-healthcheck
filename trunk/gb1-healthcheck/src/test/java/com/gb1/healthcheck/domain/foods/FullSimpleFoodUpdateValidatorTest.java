package com.gb1.healthcheck.domain.foods;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

public class FullSimpleFoodUpdateValidatorTest {
	@Test
	public void testValidate() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(new ArrayList<Food>());
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.foodRepo = foodRepo;
		v.validate(food);
	}

	@Test
	public void testValidateNameAlreadyExistsSameFood() throws Exception {
		final SimpleFood food = Foods.apple();
		final List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(food);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(food.getName())).andReturn(foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.foodRepo = foodRepo;
		v.validate(food);
	}

	@Test
	public void testValidateNameAlreadyExistsDifferentFood() {
		final SimpleFood foodWithSameName = new SimpleFood(Foods.apple());
		foodWithSameName.setId(-1L);

		List<Food> foodsWithSameName = new ArrayList<Food>();
		foodsWithSameName.add(Foods.apple());
		foodsWithSameName.add(foodWithSameName);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodsByName(foodWithSameName.getName())).andReturn(
				foodsWithSameName);
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.foodRepo = foodRepo;

		try {
			v.validate(foodWithSameName);
			fail("Food already existed under that name");
		}
		catch (FoodException expected) {
		}
	}
}
