package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;

public class ListFoodsActionTest extends TestCase {
	@SuppressWarnings("unchecked")
	public void testListFoods() {
		Set<SimpleFood> allSimpleFoods = Foods.allSimpleFoods();
		List<SimpleFood> sortedSimpleFoods = new ArrayList<SimpleFood>(allSimpleFoods);
		Collections.sort(sortedSimpleFoods, new Food.ByNameComparator());

		Set<ComplexFood> allComplexFoods = Foods.allComplexFoods();
		List<ComplexFood> sortedComplexFoods = new ArrayList<ComplexFood>(allComplexFoods);
		Collections.sort(sortedComplexFoods, new Food.ByNameComparator());

		FoodService foodSvc = EasyMock.createMock(FoodService.class);
		EasyMock.expect(foodSvc.getSimpleFoods()).andReturn(allSimpleFoods);
		EasyMock.expect(foodSvc.getComplexFoods(EasyMock.isA(IdentityHydrater.class))).andReturn(
				allComplexFoods);
		EasyMock.replay(foodSvc);

		ListFoodsAction action = new ListFoodsAction();
		action.setFoodService(foodSvc);

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(CollectionUtils.isEqualCollection(sortedSimpleFoods, action.getSimpleFoods()));
		assertTrue(CollectionUtils.isEqualCollection(sortedComplexFoods, action.getComplexFoods()));
	}
}
