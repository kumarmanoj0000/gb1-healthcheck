package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.ComplexFoodCreationRequest;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class CreateComplexFoodActionTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testPrepare() {
		final List<Food> availableIngredients = new ArrayList<Food>();
		availableIngredients.addAll(Foods.allSimpleFoods());
		availableIngredients.addAll(Foods.allComplexFoods());
		Collections.sort(availableIngredients, new Food.ByNameComparator());

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodService.getComplexFoods(EasyMock.isA(IdentityHydrater.class)))
				.andReturn(new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodService);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodService);
		action.prepare();

		assertTrue(CollectionUtils.isEqualCollection(availableIngredients, action
				.getAvailableIngredients()));
	}

	@Test
	public void testSubmit() throws FoodException {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createComplexFood(EasyMock.isA(ComplexFoodCreationRequest.class));
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitWithError() throws FoodException {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createComplexFood(EasyMock.isA(ComplexFoodCreationRequest.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException(""));
		EasyMock.replay(foodSvc);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testCancel() {
		CreateComplexFoodAction action = new CreateComplexFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
