package com.gb1.healthcheck.web.nutrition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.FullMealHydrater;
import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealUpdateRequest;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class UpdateMealActionTest extends TestCase {
	private static final String MODEL_SESSION_KEY = UpdateMealAction.class.getName() + ".model";

	@SuppressWarnings("unchecked")
	public void testInput() {
		Meal dinner = Meals.fullItalianDinner();

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock
				.expect(
						mealSvc.loadMeal(EasyMock.eq(dinner.getId()), EasyMock
								.isA(FullMealHydrater.class))).andReturn(dinner);
		EasyMock.replay(mealSvc);

		UpdateMealAction action = new UpdateMealAction();
		action.setMealService(mealSvc);

		action.setSession(new HashMap());
		action.setMealId(dinner.getId());
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(dinner.getId(), action.getMealId());
		assertEquals(dinner.getInstant(), action.getModel().getInstant());
	}

	@SuppressWarnings("unchecked")
	public void testUpdate() throws Exception {
		Meal meal = Meals.fullItalianDinner();
		MealUpdateRequest model = new BasicMealUpdateRequest(meal);

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.updateMeal(meal.getId(), model);
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		UpdateMealAction action = new UpdateMealAction();
		action.setMealService(mealSvc);

		action.setSession(session);
		action.setMealId(meal.getId());
		String result = action.update();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(MODEL_SESSION_KEY));
		EasyMock.verify(mealSvc);
	}

	@SuppressWarnings("unchecked")
	public void testUpdateWithErrors() throws MealException {
		Meal meal = Meals.fullItalianDinner();
		MealUpdateRequest model = new BasicMealUpdateRequest(meal);

		Map session = new HashMap();
		session.put(MODEL_SESSION_KEY, model);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.updateMeal(meal.getId(), model);
		EasyMock.expectLastCall().andThrow(new MealException() {
		});
		EasyMock.replay(mealSvc);

		UpdateMealAction action = new UpdateMealAction();
		action.setMealService(mealSvc);

		action.setSession(session);
		action.setMealId(meal.getId());
		String result = action.update();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasActionErrors());
		assertTrue(session.containsKey(MODEL_SESSION_KEY));
	}

	public void testCancel() {
		UpdateMealAction action = new UpdateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
