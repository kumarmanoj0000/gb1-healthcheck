package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class CreateSimpleFoodActionTest {
	@Test
	public void testSubmit() throws Exception {
		SimpleFood food = Foods.apple();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.foodService = foodSvc;
		action.model = new SimpleFoodAdapter(food);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitWithErrors() throws Exception {
		SimpleFood food = Foods.apple();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(food);
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.foodService = foodSvc;
		action.model = new SimpleFoodAdapter(food);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testCancel() {
		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
