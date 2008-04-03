package com.gb1.healthcheck.web.meals;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class DeleteMealsActionTest extends TestCase {
	public void testDeleteMeals() {
		Set<Long> mealIds = new HashSet<Long>();
		mealIds.add(Meals.fullItalianDinner().getId());

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.deleteMeals(mealIds);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealSvc);

		DeleteMealsAction action = new DeleteMealsAction();
		action.setMealService(mealSvc);
		action.setMealIds(mealIds.toArray(new Long[0]));

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(mealSvc);
	}
}
