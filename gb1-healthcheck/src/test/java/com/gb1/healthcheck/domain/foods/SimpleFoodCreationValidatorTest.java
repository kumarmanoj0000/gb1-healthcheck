package com.gb1.healthcheck.domain.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.Test;

public class SimpleFoodCreationValidatorTest {
	@Test
	public void testValidate() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(null);
		EasyMock.replay(foodRepo);

		SimpleFoodCreationValidator v = new SimpleFoodCreationValidator();
		v.foodRepo = foodRepo;
		v.validate(food);
	}

	@Test
	public void testValidateNameAlreadyTaken() throws Exception {
		final SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFoodByName(food.getName())).andReturn(food);
		EasyMock.replay(foodRepo);

		SimpleFoodCreationValidator v = new SimpleFoodCreationValidator();
		v.foodRepo = foodRepo;

		try {
			v.validate(food);
			fail("Food already existed");
		}
		catch (FoodAlreadyExistsException expected) {
			assertEquals(food.getName(), expected.getFoodName());
		}
	}
}
