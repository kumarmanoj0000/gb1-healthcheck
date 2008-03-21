package com.gb1.healthcheck.web.foods;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.foods.SimpleFoodCreationRequest;
import com.opensymphony.xwork2.Action;

public class CreateSimpleFoodActionTest extends TestCase {
	public void testSubmit() throws Exception {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(EasyMock.isA(SimpleFoodCreationRequest.class));
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}

	public void testSubmitWithErrors() throws Exception {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(EasyMock.isA(SimpleFoodCreationRequest.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}
