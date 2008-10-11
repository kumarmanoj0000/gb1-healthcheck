package com.gb1.healthcheck.web.meals;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class DeleteMealsActionTest {
	@Test
	public void testDeleteMeals() {
		List<Long> mealIds = new ArrayList<Long>();
		mealIds.add(Meals.fullItalianDinner().getId());

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.deleteMeals(mealIds);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealSvc);

		DeleteMealsAction action = new DeleteMealsAction();
		action.mealService = mealSvc;
		action.setMealIds(mealIds.toArray(new Long[0]));

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(mealSvc);
	}
}
