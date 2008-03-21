package com.gb1.healthcheck.web.foods;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class DeleteComplexFoodActionTest extends TestCase {
	public void testDelete() {
		final Long foodId = 1L;

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.deleteFood(foodId);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		DeleteComplexFoodAction action = new DeleteComplexFoodAction();
		action.setFoodService(foodSvc);

		action.setFoodId(foodId);
		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}
}
