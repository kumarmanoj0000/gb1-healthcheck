package com.gb1.healthcheck.web.nutrition;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class CreateComplexFoodActionTest extends TestCase {
	public void testInput() {
		CreateComplexFoodAction action = new CreateComplexFoodAction();
		assertEquals(Action.SUCCESS, action.input());
	}

	public void testPrepare() {
		final Set<Food> availableIngredients = new HashSet<Food>();
		availableIngredients.addAll(Foods.allSimpleFoods());
		availableIngredients.addAll(Foods.allComplexFoods());

		FoodService foodService = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodService.getSimpleFoods()).andReturn(Foods.allSimpleFoods());
		EasyMock.expect(foodService.getComplexFoods()).andReturn(Foods.allComplexFoods());
		EasyMock.replay(foodService);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodService);
		action.prepare();

		assertTrue(CollectionUtils.isEqualCollection(availableIngredients, action
				.getAvailableIngredients()));
	}

	public void testSubmit() throws FoodException {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createComplexFood(EasyMock.isA(ComplexFoodPropertyProvider.class));
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.SUCCESS, action.submit());
		EasyMock.verify(foodSvc);
	}

	public void testSubmitWithError() throws FoodException {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createComplexFood(EasyMock.isA(ComplexFoodPropertyProvider.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException(""));
		EasyMock.replay(foodSvc);

		CreateComplexFoodAction action = new CreateComplexFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.INPUT, action.submit());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		CreateComplexFoodAction action = new CreateComplexFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}