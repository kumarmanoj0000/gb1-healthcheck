package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class DeleteComplexFoodsActionTest {
	@Test
	public void testDeleteFoods() {
		List<Food> foods = new ArrayList<Food>();
		foods.add(Foods.beefStock());
		foods.add(Foods.redWine());

		Set<Long> foodIds = new HashSet<Long>();
		foodIds.add(Foods.beefStock().getId());
		foodIds.add(Foods.redWine().getId());

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		foodSvc.deleteFoods(foodIds);
		EasyMock.expectLastCall();
		EasyMock.replay(foodSvc);

		DeleteComplexFoodsAction action = new DeleteComplexFoodsAction();
		action.foodService = foodSvc;
		action.setFoodIds(foodIds.toArray(new Long[0]));

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(foodSvc);
	}
}
