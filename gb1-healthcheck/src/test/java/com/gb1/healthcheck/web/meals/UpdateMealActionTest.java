package com.gb1.healthcheck.web.meals;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class UpdateMealActionTest extends TestCase {
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
		action.setMealId(dinner.getId());
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(dinner.getId(), action.getMealId());
		assertEquals(dinner.getInstant(), action.getModel().getInstant());
	}

	@SuppressWarnings("unchecked")
	public void testUpdate() throws Exception {
		Meal meal = Meals.fullItalianDinner();
		BasicMealUpdateRequest model = new BasicMealUpdateRequest(meal);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.updateMeal(model);
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		UpdateMealAction action = new UpdateMealAction();
		action.setMealService(mealSvc);
		action.setMealId(meal.getId());
		action.setModel(model);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(mealSvc);
	}

	@SuppressWarnings("unchecked")
	public void testUpdateWithErrors() throws MealException {
		Meal meal = Meals.fullItalianDinner();
		BasicMealUpdateRequest model = new BasicMealUpdateRequest(meal);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.updateMeal(model);
		EasyMock.expectLastCall().andThrow(new MealException() {
		});
		EasyMock.replay(mealSvc);

		UpdateMealAction action = new UpdateMealAction();
		action.setMealService(mealSvc);
		action.setMealId(meal.getId());
		action.setModel(model);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasActionErrors());
	}

	public void testCancel() {
		UpdateMealAction action = new UpdateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
