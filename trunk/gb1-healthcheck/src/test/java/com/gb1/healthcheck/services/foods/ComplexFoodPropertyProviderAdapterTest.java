package com.gb1.healthcheck.services.foods;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;

public class ComplexFoodPropertyProviderAdapterTest extends TestCase {
	public void testAdapt() {
		final Map<Long, Food> foods = new HashMap<Long, Food>();
		foods.put(Foods.beef().getId(), Foods.beef());
		foods.put(Foods.apple().getId(), Foods.apple());

		ComplexFoodCreationRequest request = new ComplexFoodCreationRequest() {
			public Set<Long> getIngredientIds() {
				return foods.keySet();
			}

			public String getName() {
				return "name";
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (Long id : foods.keySet()) {
			EasyMock.expect(foodRepo.loadFood(id)).andReturn(foods.get(id));
		}
		EasyMock.replay(foodRepo);

		ComplexFoodCreationPropertyProviderAdapter adapter = new ComplexFoodCreationPropertyProviderAdapter(
				request);
		adapter.setFoodRepository(foodRepo);

		assertEquals(request.getName(), adapter.getName());
		assertTrue(CollectionUtils.isEqualCollection(foods.values(), adapter.getIngredients()));
	}
}
