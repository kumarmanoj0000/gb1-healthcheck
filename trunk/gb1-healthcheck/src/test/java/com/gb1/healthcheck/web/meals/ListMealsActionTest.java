package com.gb1.healthcheck.web.meals;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class ListMealsActionTest extends TestCase {
	public void testListMeals() {
		final User requester = Users.gb();
		final List<Meal> mealHistory = Meals.mealHistory();

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.getMealHistory(requester)).andReturn(mealHistory);
		EasyMock.replay(mealSvc);

		ListMealsAction action = new ListMealsAction() {
			@Override
			protected User getRequester() {
				return requester;
			}
		};
		action.setMealService(mealSvc);

		assertEquals(Action.SUCCESS, action.list());
		assertTrue(CollectionUtils.isEqualCollection(mealHistory, action.getMealHistory()));
	}
}
