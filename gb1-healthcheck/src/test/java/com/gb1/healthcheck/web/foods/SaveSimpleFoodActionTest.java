package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class SaveSimpleFoodActionTest {
	@Test
	public void testCancel() {
		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.setSession(new HashMap<String, Object>());
		assertEquals(Action.SUCCESS, action.cancel());
	}

	@Test
	public void testInput() throws Exception {
		final SimpleFood apple = Foods.apple();

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.findSimpleFood(apple.getId())).andReturn(apple);
		EasyMock.replay(foodSvc);

		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(new HashMap<String, Object>());
		action.setFoodId(apple.getId());
		String result = action.input();

		assertEquals(Action.INPUT, result);
		assertEquals(apple.getId(), action.getModel().getTarget().getId());
		assertEquals(apple.getName(), action.getModel().getName());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitCreate() throws Exception {
		SimpleFood food = new SimpleFood(Foods.apple());

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SaveSimpleFoodAction.MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.foodService = foodSvc;
		action.setSession(sessionMap);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitCreateWithErrors() throws Exception {
		SimpleFood food = new SimpleFood(Foods.apple());

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SaveSimpleFoodAction.MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(food);
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.foodService = foodSvc;
		action.setSession(sessionMap);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitUpdate() throws Exception {
		SimpleFood food = Foods.apple();

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(SaveSimpleFoodAction.MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(session);
		action.setFoodId(food.getId());
		String result = action.execute();

		assertEquals(Action.SUCCESS, result);
		assertFalse(session.containsKey(SaveSimpleFoodAction.MODEL_SESSION_KEY));
		EasyMock.verify(foodSvc);
	}

	@Test
	public void testSubmitUpdateWithErrors() throws Exception {
		SimpleFood food = Foods.apple();

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(SaveSimpleFoodAction.MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.updateSimpleFood(food);
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		SaveSimpleFoodAction action = new SaveSimpleFoodAction();
		action.foodService = foodSvc;

		action.setSession(session);
		action.setFoodId(food.getId());
		String result = action.execute();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}
}
