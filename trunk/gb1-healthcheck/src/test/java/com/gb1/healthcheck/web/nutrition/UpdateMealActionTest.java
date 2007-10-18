package com.gb1.healthcheck.web.nutrition;

import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.FullMealHydrater;
import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class UpdateMealActionTest extends TestCase {
	@SuppressWarnings("unchecked")
	public void testInput() {
		final Meal dinner = new Meal(new Date()).addDish(Meals.spaghettiDish());

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

		assertEquals(Action.SUCCESS, result);
		assertEquals(dinner.getId(), action.getMealId());
		assertEquals(dinner.getInstant(), action.getModel().getInstant());
	}

	public void testCancel() {
		UpdateMealAction action = new UpdateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}

	public void testUpdate() {
		UpdateMealAction action = new UpdateMealAction();
		assertEquals(Action.SUCCESS, action.update());
	}
}
