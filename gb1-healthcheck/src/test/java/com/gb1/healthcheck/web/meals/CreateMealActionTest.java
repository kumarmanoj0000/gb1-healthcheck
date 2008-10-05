package com.gb1.healthcheck.web.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.IdentityHydrater;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class CreateMealActionTest {
	@Test
	public void testCancel() {
		CreateMealAction action = new CreateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPrepare() {
		List<Food> availableFoods = new LinkedList<Food>();
		availableFoods.addAll(Foods.allSimpleFoods());
		availableFoods.addAll(Foods.allComplexFoods());
		Collections.sort(availableFoods, new Food.ByNameComparator());

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class)))
				.andReturn(new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		CreateMealAction action = new CreateMealAction();
		action.foodService = foodService;
		action.setRequester(Users.lg());

		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(PreparationMethod.values()),
				action.getAvailablePreparationMethods()));
		assertTrue(CollectionUtils.isEqualCollection(availableFoods, action.getAvailableFoods()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSubmit() throws MealException {
		User requester = Users.lg();

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class)))
				.andReturn(new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(Meal.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.foodService = foodService;
		action.mealService = mealSvc;
		action.setRequester(requester);
		action.setSession(new HashMap<String, Object>());

		action.input();
		assertEquals(Action.SUCCESS, action.execute());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSubmitWithErrors() throws MealException {
		User requester = Users.lg();

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class)))
				.andReturn(new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(Meal.class));
		EasyMock.expectLastCall().andThrow(new MealAlreadyExistsException());
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.foodService = foodService;
		action.mealService = mealSvc;
		action.setRequester(requester);
		action.setSession(new HashMap<String, Object>());

		action.input();
		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
	}
}
