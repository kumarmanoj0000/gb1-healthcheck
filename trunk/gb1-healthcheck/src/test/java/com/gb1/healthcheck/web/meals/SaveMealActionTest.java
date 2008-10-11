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
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class SaveMealActionTest {
	@Test
	public void testCancel() {
		SaveMealAction action = new SaveMealAction();
		action.setSession(new HashMap<String, Object>());
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@Test
	public void testPrepare() {
		List<Food> availableFoods = new LinkedList<Food>();
		availableFoods.addAll(Foods.allSimpleFoods());
		availableFoods.addAll(Foods.allComplexFoods());
		Collections.sort(availableFoods, new Food.ByNameComparator());

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.findAllSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.findAllComplexFoods()).andReturn(
				new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		SaveMealAction action = new SaveMealAction();
		action.foodService = foodService;
		action.prepare();

		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(PreparationMethod.values()),
				action.getAvailablePreparationMethods()));
		assertTrue(CollectionUtils.isEqualCollection(availableFoods, action.getAvailableFoods()));
	}

	@Test
	public void testSubmitCreate() throws MealException {
		User requester = Users.lg();

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.findAllSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.findAllComplexFoods()).andReturn(
				new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(Meal.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		SaveMealAction action = new SaveMealAction();
		action.foodService = foodService;
		action.mealService = mealSvc;
		action.setRequester(requester);
		action.setSession(new HashMap<String, Object>());

		action.input();
		assertEquals(Action.SUCCESS, action.execute());
	}

	@Test
	public void testSubmitCreateWithErrors() throws MealException {
		User requester = Users.lg();

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.findAllSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.findAllComplexFoods()).andReturn(
				new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(Meal.class));
		EasyMock.expectLastCall().andThrow(new MealAlreadyExistsException());
		EasyMock.replay(mealSvc);

		SaveMealAction action = new SaveMealAction();
		action.foodService = foodService;
		action.mealService = mealSvc;
		action.setRequester(requester);
		action.setSession(new HashMap<String, Object>());

		action.input();
		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
	}

	@Test
	public void testInput() {
		Meal dinner = Meals.fullItalianDinner();

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.findMeal(EasyMock.eq(dinner.getId()))).andReturn(dinner);
		EasyMock.replay(mealSvc);

		SaveMealAction action = new SaveMealAction();
		action.mealService = mealSvc;
		action.setSession(new HashMap<String, Object>());
		action.setMealId(dinner.getId());

		assertEquals(Action.INPUT, action.input());
		assertEquals(dinner.getId(), action.getModel().getId());
		assertEquals(dinner.getInstant(), action.getModel().getInstant());
	}

	@Test
	public void testSubmitUpdate() throws Exception {
		Meal meal = Meals.fullItalianDinner();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFood(Foods.spaghetti().getId())).andReturn(Foods.spaghetti());
		EasyMock.expect(foodSvc.findFood(Foods.redWine().getId())).andReturn(Foods.redWine());
		EasyMock.replay(foodSvc);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.findMeal(EasyMock.eq(meal.getId()))).andReturn(meal);
		mealSvc.updateMeal(meal);
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		SaveMealAction action = new SaveMealAction();
		action.foodService = foodSvc;
		action.mealService = mealSvc;
		action.setSession(new HashMap<String, Object>());
		action.setMealId(meal.getId());

		action.input();
		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(mealSvc);
	}

	@Test
	public void testSubmitUpdateWithErrors() throws MealException {
		Meal meal = Meals.fullItalianDinner();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFood(Foods.spaghetti().getId())).andReturn(Foods.spaghetti());
		EasyMock.expect(foodSvc.findFood(Foods.redWine().getId())).andReturn(Foods.redWine());
		EasyMock.replay(foodSvc);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		EasyMock.expect(mealSvc.findMeal(EasyMock.eq(meal.getId()))).andReturn(meal);
		mealSvc.updateMeal(meal);
		EasyMock.expectLastCall().andThrow(new MealException() {
		});
		EasyMock.replay(mealSvc);

		SaveMealAction action = new SaveMealAction();
		action.foodService = foodSvc;
		action.mealService = mealSvc;
		action.setSession(new HashMap<String, Object>());
		action.setMealId(meal.getId());

		action.input();
		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasActionErrors());
	}
}
