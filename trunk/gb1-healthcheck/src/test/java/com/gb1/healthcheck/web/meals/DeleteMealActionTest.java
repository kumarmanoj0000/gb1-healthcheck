package com.gb1.healthcheck.web.meals;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class DeleteMealActionTest extends TestCase {
	public void testDelete() {
		final Long mealId = 1L;

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.deleteMeal(mealId);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealSvc);

		DeleteMealAction action = new DeleteMealAction();
		action.setMealService(mealSvc);
		action.setMealId(mealId);

		assertEquals(Action.SUCCESS, action.delete());
		EasyMock.verify(mealSvc);
	}
}
