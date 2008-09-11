package com.gb1.healthcheck.web.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class ManageMealsActionTest {
	@Test
	public void testListMeals() {
		final User requester = Users.gb();
		final List<Meal> mealHistory = Meals.mealHistory();

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.getMealHistory(requester)).andReturn(mealHistory);
		EasyMock.replay(mealSvc);

		Map<String, Object> sessionMap = new HashMap<String, Object>();

		ManageMealsAction action = new ManageMealsAction();
		action.setRequester(requester);
		action.setSession(sessionMap);
		action.setMealService(mealSvc);

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(mealHistory, action.getMeals()));
		assertTrue(sessionMap.containsValue(mealHistory));

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(mealHistory, action.getMeals()));
		assertTrue(sessionMap.containsValue(mealHistory));

		EasyMock.verify(mealSvc);
	}
}
