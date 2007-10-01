package com.gb1.healthcheck.domain.nutrition;

import junit.framework.TestCase;

import org.easymock.EasyMock;

public class FullSimpleFoodUpdateValidatorTest extends TestCase {
	public void testValidate() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(null);
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.setFoodRepository(foodRepo);
		v.validate(food);
	}

	public void testValidateNameAlreadyExistsSameFood() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(food);
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.setFoodRepository(foodRepo);
		v.validate(food);
	}

	public void testValidateNameAlreadyExistsDifferentFood() {
		final SimpleFood foodWithSameName = new SimpleFood(Foods.apple());
		foodWithSameName.setId(-1L);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(foodWithSameName.getName())).andReturn(
				Foods.apple());
		EasyMock.replay(foodRepo);

		FullSimpleFoodUpdateValidator v = new FullSimpleFoodUpdateValidator();
		v.setFoodRepository(foodRepo);

		try {
			v.validate(foodWithSameName);
			fail("Food already existed under that name");
		}
		catch (FoodException expected) {
		}
	}
}
