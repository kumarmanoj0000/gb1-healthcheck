package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.MealCreationRequest;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class CreateMealActionTest extends TestCase {
	public void testCancel() {
		CreateMealAction action = new CreateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@SuppressWarnings("unchecked")
	public void testPrepare() {
		final List<Food> availableFoods = new LinkedList<Food>();
		availableFoods.addAll(Foods.allSimpleFoods());
		availableFoods.addAll(Foods.allComplexFoods());
		Collections.sort(availableFoods, new Food.ByNameComparator());

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(Foods.allSimpleFoods());
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class))).andReturn(
				Foods.allComplexFoods());
		EasyMock.replay(foodService);

		CreateMealAction action = new CreateMealAction();
		action.setFoodService(foodService);
		action.prepare();

		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(PreparationMethod.values()),
				action.getAvailablePreparationMethods()));
		assertTrue(CollectionUtils.isEqualCollection(availableFoods, action.getAvailableFoods()));
	}

	public void testSubmit() throws MealException {
		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(MealCreationRequest.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.setMealService(mealSvc);

		assertEquals(Action.SUCCESS, action.submit());
	}

	public void testSubmitWithErrors() throws MealException {
		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(MealCreationRequest.class));
		EasyMock.expectLastCall().andThrow(new MealAlreadyExistsException());
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.setMealService(mealSvc);

		assertEquals(Action.INPUT, action.submit());
		assertTrue(action.hasFieldErrors());
	}
}
