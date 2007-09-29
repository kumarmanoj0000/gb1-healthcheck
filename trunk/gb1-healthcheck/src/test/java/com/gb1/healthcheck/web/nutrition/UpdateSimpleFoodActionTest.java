package com.gb1.healthcheck.web.nutrition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodActionTest extends TestCase {
	@SuppressWarnings("unchecked")
	public void testPrepareUpdateSimpleFood() throws Exception {
		final Long foodId = 1L;

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.loadSimpleFood(foodId)).andReturn(Foods.apple());
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(new HashMap());
		action.setFoodId(foodId);
		String result = action.prepareSimpleFoodUpdate();

		assertEquals(Action.SUCCESS, result);
		assertEquals(foodId, action.getFoodId());
		assertEquals(Foods.apple().getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testUpdateSimpleFood() throws Exception {
		final Long foodId = 1L;
		final SimpleFood apple = Foods.apple();
		SimpleFoodUpdateRequest model = new SimpleFoodUpdateRequest(apple);

		final String modelSessionKey = UpdateSimpleFoodAction.class.getName() + ".model";
		Map session = new HashMap();
		session.put(modelSessionKey, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(foodId, model);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(foodId);
		String result = action.updateSimpleFood();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(modelSessionKey));
		EasyMock.verify(foodSvc);
	}
}
