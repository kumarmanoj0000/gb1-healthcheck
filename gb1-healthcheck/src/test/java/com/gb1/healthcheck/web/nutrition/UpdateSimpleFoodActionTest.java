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
	public void testInput() throws Exception {
		final Long foodId = 1L;
		final SimpleFood apple = Foods.apple();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.loadSimpleFood(foodId)).andReturn(apple);
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(new HashMap());
		action.setFoodId(foodId);
		String result = action.input();

		assertEquals(Action.SUCCESS, result);
		assertEquals(foodId, action.getFoodId());
		assertEquals(apple.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testSubmit() throws Exception {
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
		String result = action.submit();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(modelSessionKey));
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
