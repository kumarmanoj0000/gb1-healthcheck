package com.gb1.healthcheck.web.nutrition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodActionTest extends TestCase {
	private static final String MODEL_SESSION_KEY = UpdateSimpleFoodAction.class.getName()
			+ ".model";

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
		SimpleFoodUpdateRequest model = new SimpleFoodUpdateRequest(Foods.apple());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

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
		assertFalse(session.containsKey(MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testSubmitWithErrors() throws Exception {
		final Long foodId = 1L;
		SimpleFoodUpdateRequest model = new SimpleFoodUpdateRequest(Foods.apple());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(EasyMock.eq(foodId), EasyMock
				.isA(SimpleFoodMutablePropertyProvider.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(foodId);
		String result = action.submit();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		UpdateSimpleFoodAction action = new UpdateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
