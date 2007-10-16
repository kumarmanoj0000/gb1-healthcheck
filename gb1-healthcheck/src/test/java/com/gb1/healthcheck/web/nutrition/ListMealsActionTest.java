package com.gb1.healthcheck.web.nutrition;

import java.text.ParseException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class ListMealsActionTest extends TestCase {
	public void testListMeals() throws ParseException {
		final List<Meal> mealHistory = Meals.mealHistory();

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.getMealHistory()).andReturn(mealHistory);
		EasyMock.replay(mealSvc);

		ListMealsAction action = new ListMealsAction();
		action.setMealService(mealSvc);

		assertEquals(Action.SUCCESS, action.listMeals());
		assertTrue(CollectionUtils.isEqualCollection(mealHistory, action.getMealHistory()));
	}
}
