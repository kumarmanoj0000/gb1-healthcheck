package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

public class CreateMealActionTest extends TestCase {
	private final List<Food> availableFoods = new LinkedList<Food>();
	private FoodService foodService;

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		availableFoods.addAll(Foods.allSimpleFoods());
		availableFoods.addAll(Foods.allComplexFoods());
		Collections.sort(availableFoods, new Food.ByNameComparator());

		foodService = EasyMock.createNiceMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(Foods.allSimpleFoods());
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class)))
				.andReturn(Foods.allComplexFoods());
		EasyMock.replay(foodService);
	}

	public void testCancel() {
		CreateMealAction action = new CreateMealAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@SuppressWarnings("unchecked")
	public void testPrepare() {
		CreateMealAction action = new CreateMealAction();
		action.setFoodService(foodService);
		action.setRequester(Users.lg());
		action.prepare();

		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(PreparationMethod.values()),
				action.getAvailablePreparationMethods()));
		assertTrue(CollectionUtils.isEqualCollection(availableFoods, action.getAvailableFoods()));
	}

	public void testSubmit() throws MealException {
		User requester = Users.lg();
		BasicMealCreationRequest model = new BasicMealCreationRequest(requester);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(model);
		EasyMock.expectLastCall();
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.setFoodService(foodService);
		action.setMealService(mealSvc);
		action.setRequester(requester);
		action.setModel(model);

		assertEquals(Action.SUCCESS, action.execute());
	}

	public void testSubmitWithErrors() throws MealException {
		User requester = Users.lg();
		BasicMealCreationRequest model = new BasicMealCreationRequest(requester);

		MealService mealSvc = EasyMock.createMock(MealService.class);
		mealSvc.createMeal(EasyMock.isA(MealCreationRequest.class));
		EasyMock.expectLastCall().andThrow(new MealAlreadyExistsException());
		EasyMock.replay(mealSvc);

		CreateMealAction action = new CreateMealAction();
		action.setFoodService(foodService);
		action.setMealService(mealSvc);
		action.setRequester(requester);
		action.setModel(model);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
	}
}
