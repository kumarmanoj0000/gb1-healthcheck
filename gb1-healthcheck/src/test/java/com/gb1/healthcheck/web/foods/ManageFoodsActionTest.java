package com.gb1.healthcheck.web.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.IdentityHydrater;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class ManageFoodsActionTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testListFoods() {
		List<SimpleFood> allSimpleFoods = new ArrayList<SimpleFood>(Foods.allSimpleFoods());
		List<ComplexFood> allComplexFoods = new ArrayList<ComplexFood>(Foods.allComplexFoods());

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.getSimpleFoods()).andReturn(
				new ArrayList<SimpleFood>(Foods.allSimpleFoods()));
		EasyMock.expect(foodSvc.getComplexFoods(EasyMock.isA(IdentityHydrater.class))).andReturn(
				new ArrayList<ComplexFood>(Foods.allComplexFoods()));
		EasyMock.replay(foodSvc);

		Map<String, Object> sessionMap = new HashMap<String, Object>();

		ManageFoodsAction action = new ManageFoodsAction();
		action.setSession(sessionMap);
		action.foodService = foodSvc;

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(allSimpleFoods, action.getSimpleFoods()));
		assertTrue(CollectionUtils.isEqualCollection(allComplexFoods, action.getComplexFoods()));
		assertTrue(sessionMap.containsValue(allSimpleFoods));
		assertTrue(sessionMap.containsValue(allComplexFoods));

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(allSimpleFoods, action.getSimpleFoods()));
		assertTrue(CollectionUtils.isEqualCollection(allComplexFoods, action.getComplexFoods()));
		assertTrue(sessionMap.containsValue(allSimpleFoods));
		assertTrue(sessionMap.containsValue(allComplexFoods));

		// make sure that loading the food list occurs only once
		EasyMock.verify(foodSvc);
	}
}
