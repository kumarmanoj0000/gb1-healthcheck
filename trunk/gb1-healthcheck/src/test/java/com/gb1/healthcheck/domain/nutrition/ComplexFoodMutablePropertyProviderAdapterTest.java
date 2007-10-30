package com.gb1.healthcheck.domain.nutrition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

public class ComplexFoodMutablePropertyProviderAdapterTest extends TestCase {
	public void testAdapt() {
		final String name = "name";
		final Map<Long, Food> foods = new HashMap<Long, Food>();
		foods.put(Foods.beef().getId(), Foods.beef());
		foods.put(Foods.apple().getId(), Foods.apple());

		ComplexFoodUpdateRequest request = new ComplexFoodUpdateRequest() {
			public Set<Long> getIngredientIds() {
				return foods.keySet();
			}

			public String getName() {
				return name;
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (Long id : foods.keySet()) {
			EasyMock.expect(foodRepo.loadFood(id)).andReturn(foods.get(id));
		}
		EasyMock.replay(foodRepo);

		ComplexFoodMutablePropertyProviderAdapter adapter = new ComplexFoodMutablePropertyProviderAdapter(
				request);
		adapter.setFoodRepository(foodRepo);

		assertEquals(name, adapter.getName());
		assertTrue(CollectionUtils.isEqualCollection(foods.values(), adapter.getIngredients()));
	}
}
