package com.gb1.healthcheck.domain.nutrition;

import org.easymock.EasyMock;

import junit.framework.TestCase;

public class FullSimpleFoodCreationValidatorTest extends TestCase {
	public void testValidate() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(null);
		EasyMock.replay(foodRepo);

		FullSimpleFoodCreationValidator v = new FullSimpleFoodCreationValidator();
		v.setFoodRepository(foodRepo);
		v.validate(food);
	}

	public void testValidateNameAlreadyTaken() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(food);
		EasyMock.replay(foodRepo);

		FullSimpleFoodCreationValidator v = new FullSimpleFoodCreationValidator();
		v.setFoodRepository(foodRepo);

		try {
			v.validate(food);
			fail("Food already existed");
		}
		catch (FoodAlreadyExistsException expected) {
			assertEquals(food.getName(), expected.getFoodName());
		}
	}
}
