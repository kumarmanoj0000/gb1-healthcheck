package com.gb1.healthcheck.web.foods;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.services.foods.ComplexFoodUpdateRequest;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.foods.FullComplexFoodHydrater;
import com.opensymphony.xwork2.Action;

public class UpdateComplexFoodActionTest extends TestCase {
	private static final String MODEL_SESSION_KEY = UpdateComplexFoodAction.class.getName()
			+ ".model";

	@SuppressWarnings("unchecked")
	public void testInput() {
		final ComplexFood spag = Foods.spaghetti();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(
				foodSvc.loadComplexFood(EasyMock.eq(spag.getId()), EasyMock
						.isA(FullComplexFoodHydrater.class))).andReturn(spag);
		EasyMock.replay(foodSvc);

		UpdateComplexFoodAction action = new UpdateComplexFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(new HashMap());
		action.setFoodId(spag.getId());
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(spag.getId(), action.getFoodId());
		assertEquals(spag.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testSubmit() throws Exception {
		BasicComplexFoodUpdateRequest model = new BasicComplexFoodUpdateRequest(Foods.spaghetti());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateComplexFood(model);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		UpdateComplexFoodAction action = new UpdateComplexFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(model.getFoodId());
		String result = action.execute();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testSubmitWithErrors() throws Exception {
		BasicComplexFoodUpdateRequest model = new BasicComplexFoodUpdateRequest(Foods.spaghetti());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateComplexFood(EasyMock.isA(ComplexFoodUpdateRequest.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("spaghetti"));
		EasyMock.replay(foodSvc);

		UpdateComplexFoodAction action = new UpdateComplexFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(model.getFoodId());
		String result = action.execute();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		ComplexFoodActionSupport action = new UpdateComplexFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
