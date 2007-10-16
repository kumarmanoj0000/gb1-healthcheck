package com.gb1.healthcheck.web.nutrition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.FullComplexFoodHydrater;
import com.gb1.healthcheck.services.nutrition.FoodService;
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

		assertEquals(Action.SUCCESS, result);
		assertEquals(spag.getId(), action.getFoodId());
		assertEquals(spag.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@SuppressWarnings("unchecked")
	public void testSubmit() throws Exception {
		final Long foodId = 1L;
		ComplexFoodUpdateRequest model = new ComplexFoodUpdateRequest(Foods.spaghetti());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateComplexFood(foodId, model);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		UpdateComplexFoodAction action = new UpdateComplexFoodAction();
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
		ComplexFoodUpdateRequest model = new ComplexFoodUpdateRequest(Foods.spaghetti());

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateComplexFood(EasyMock.eq(foodId), EasyMock
				.isA(ComplexFoodMutablePropertyProvider.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("spaghetti"));
		EasyMock.replay(foodSvc);

		UpdateComplexFoodAction action = new UpdateComplexFoodAction();
		action.setFoodService(foodSvc);

		action.setSession(session);
		action.setFoodId(foodId);
		String result = action.submit();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		ComplexFoodActionSupport action = new UpdateComplexFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
