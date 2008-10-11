package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.foods.FullComplexFoodHydrater;
import com.opensymphony.xwork2.Action;

public class SaveComplexFoodActionTest {
	@Test
	public void testSubmitCreate() throws FoodException {
		ComplexFood food = new ComplexFood(Foods.spaghetti());

		List<Long> foodIds = new ArrayList<Long>();
		for (Food ingredient : food.getIngredients()) {
			foodIds.add(ingredient.getId());
		}

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SaveComplexFoodAction.MODEL_SESSION_KEY, new ComplexFoodBuilder(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFoods(foodIds)).andReturn(food.getIngredients());
		foodSvc.createComplexFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.foodService = foodSvc;
		action.setSession(sessionMap);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitCreateWithError() throws FoodException {
		ComplexFood food = new ComplexFood(Foods.spaghetti());

		List<Long> foodIds = new ArrayList<Long>();
		for (Food ingredient : food.getIngredients()) {
			foodIds.add(ingredient.getId());
		}

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SaveComplexFoodAction.MODEL_SESSION_KEY, new ComplexFoodBuilder(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFoods(foodIds)).andReturn(food.getIngredients());
		foodSvc.createComplexFood(EasyMock.isA(ComplexFood.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException(""));
		EasyMock.replay(foodSvc);

		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.foodService = foodSvc;
		action.setSession(sessionMap);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testCancel() {
		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.setSession(new HashMap<String, Object>());
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@Test
	public void testInputUpdate() {
		final ComplexFood spag = Foods.spaghetti();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(
				foodSvc.findComplexFood(EasyMock.eq(spag.getId()), EasyMock
						.isA(FullComplexFoodHydrater.class))).andReturn(spag);
		EasyMock.replay(foodSvc);

		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.foodService = foodSvc;

		action.setSession(new HashMap<String, Object>());
		action.setFoodId(spag.getId());

		assertEquals(Action.INPUT, action.input());
		assertEquals(spag.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitUpdate() throws Exception {
		ComplexFood food = Foods.spaghetti();

		List<Long> foodIds = new ArrayList<Long>();
		for (Food ingredient : food.getIngredients()) {
			foodIds.add(ingredient.getId());
		}

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(SaveComplexFoodAction.MODEL_SESSION_KEY, new ComplexFoodBuilder(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFoods(foodIds)).andReturn(food.getIngredients());
		foodSvc.updateComplexFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.foodService = foodSvc;
		action.setSession(session);
		action.setFoodId(food.getId());

		assertEquals(Action.SUCCESS, action.execute());
		assertFalse(session.containsKey(SaveComplexFoodAction.MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitUpdateWithErrors() throws Exception {
		ComplexFood food = Foods.spaghetti();

		List<Long> foodIds = new ArrayList<Long>();
		for (Food ingredient : food.getIngredients()) {
			foodIds.add(ingredient.getId());
		}

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(SaveComplexFoodAction.MODEL_SESSION_KEY, new ComplexFoodBuilder(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findFoods(foodIds)).andReturn(food.getIngredients());
		foodSvc.updateComplexFood(EasyMock.isA(ComplexFood.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("spaghetti"));
		EasyMock.replay(foodSvc);

		SaveComplexFoodAction action = new SaveComplexFoodAction();
		action.foodService = foodSvc;

		action.setSession(session);
		action.setFoodId(food.getId());

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}
}
