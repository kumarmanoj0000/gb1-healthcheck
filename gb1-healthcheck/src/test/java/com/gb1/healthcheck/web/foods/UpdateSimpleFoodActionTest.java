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
import com.gb1.healthcheck.services.foods.SimpleFoodUpdateRequest;
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
		action.setFoodService(foodSvc);

		action.setSession(new HashMap());
		action.setFoodId(foodId);
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(foodId, action.getFoodId());
		assertEquals(apple.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmit() throws Exception {
		BasicSimpleFoodUpdateRequest model = new BasicSimpleFoodUpdateRequest(Foods.apple());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(model);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(model.getFoodId());
		String result = action.execute();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmitWithErrors() throws Exception {
		BasicSimpleFoodUpdateRequest model = new BasicSimpleFoodUpdateRequest(Foods.apple());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(EasyMock.isA(SimpleFoodUpdateRequest.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(model.getFoodId());
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
