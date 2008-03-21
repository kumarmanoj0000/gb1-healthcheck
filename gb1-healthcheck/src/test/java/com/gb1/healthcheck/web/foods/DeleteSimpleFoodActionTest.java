package com.gb1.healthcheck.web.foods;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class DeleteSimpleFoodActionTest extends TestCase {
	public void testDelete() {
		final Long foodId = 1L;

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.deleteFood(foodId);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		DeleteSimpleFoodAction action = new DeleteSimpleFoodAction();
		action.setFoodService(foodSvc);

		action.setFoodId(foodId);
		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}
}
