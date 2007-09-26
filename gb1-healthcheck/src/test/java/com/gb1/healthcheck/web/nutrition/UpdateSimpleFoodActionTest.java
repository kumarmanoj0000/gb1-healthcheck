package com.gb1.healthcheck.web.nutrition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodActionTest extends TestCase {
	public void testPrepareUpdateSimpleFood() {
		final Long foodId = 1L;

		Map<String, String> params = new HashMap<String, String>();
		params.put("foodId", Long.toString(foodId));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.loadSimpleFood(foodId)).andReturn(Foods.apple());
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);
		action.setParameters(params);

		assertEquals(Action.SUCCESS, action.prepareUpdateSimpleFood());
		assertEquals(foodId, action.getFoodId());
		assertEquals(Foods.apple().getName(), action.getModel().getName());
	}

	public void testUpdateSimpleFood() throws Exception {
		final Long foodId = 1L;

		Map<String, String> params = new HashMap<String, String>();
		params.put("foodId", Long.toString(foodId));

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.loadSimpleFood(foodId)).andReturn(Foods.apple());
		foodService.updateSimpleFood(EasyMock.eq(foodId), EasyMock
				.isA(SimpleFoodUpdateRequest.class));
		EasyMock.expectLastCall();
		EasyMock.replay(foodService);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodService);
		action.setParameters(params);
		action.prepareUpdateSimpleFood();

		assertEquals(Action.SUCCESS, action.updateSimpleFood());
		EasyMock.verify(foodService);
	}
}
