package com.gb1.healthcheck.web.nutrition;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class CreateSimpleFoodActionTest extends TestCase {
	public void testSubmit() throws Exception {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(EasyMock.isA(SimpleFoodPropertyProvider.class));
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.SUCCESS, action.submit());
		EasyMock.verify(foodSvc);
	}

	public void testSubmitWithErrors() throws Exception {
		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.createSimpleFood(EasyMock.isA(SimpleFoodPropertyProvider.class));
		EasyMock.expectLastCall().andThrow(new FoodAlreadyExistsException("apple"));
		EasyMock.replay(foodSvc);

		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.INPUT, action.submit());
		assertTrue(action.hasFieldErrors());
		EasyMock.verify(foodSvc);
	}

	public void testCancel() {
		CreateSimpleFoodAction action = new CreateSimpleFoodAction();
		assertEquals(Action.SUCCESS, action.cancel());
	}
}