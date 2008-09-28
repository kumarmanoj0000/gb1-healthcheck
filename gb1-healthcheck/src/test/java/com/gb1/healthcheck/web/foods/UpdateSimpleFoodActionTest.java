package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodActionTest {
	private static final String MODEL_SESSION_KEY = UpdateSimpleFoodAction.class.getName()
			+ ".model";

	@SuppressWarnings("unchecked")
	@Test
	public void testInput() throws Exception {
		final Long foodId = 1L;
		final SimpleFood apple = Foods.apple();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.getSimpleFood(foodId)).andReturn(apple);
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(new HashMap());
		action.setFoodId(foodId);
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(foodId, action.getFoodId());
		assertEquals(apple.getName(), action.getFood().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmit() throws Exception {
		SimpleFood food = Foods.apple();

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(session);
		action.setFoodId(food.getId());
		String result = action.execute();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmitWithErrors() throws Exception {
		SimpleFood food = Foods.apple();

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(food);
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(session);
		action.setFoodId(food.getId());
		String result = action.execute();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testCancel() {
		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
