package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

public class MealMutablePropertyProviderAdapterTest extends TestCase {
	public void testAdapt() {
		final Map<Long, PreparedFood> dishes = new HashMap<Long, PreparedFood>();
		dishes.put(Meals.redWineDrink().getId(), Meals.redWineDrink());
		dishes.put(Meals.spaghettiDish().getId(), Meals.spaghettiDish());

		MealUpdateRequest request = new MealUpdateRequest() {
			public Set<PreparedFoodUpdateRequest> getDishUpdateRequests() {
				Set<PreparedFoodUpdateRequest> reqs = new HashSet<PreparedFoodUpdateRequest>();
				for (final PreparedFood dish : dishes.values()) {
					reqs.add(new PreparedFoodUpdateRequest() {
						public Long getIngredientId() {
							return dish.getIngredient().getId();
						}

						public PreparationMethod getPreparationMethod() {
							return dish.getPreparationMethod();
						}
					});
				}

				return reqs;
			}

			public Date getInstant() {
				return new Date();
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (PreparedFood dish : dishes.values()) {
			EasyMock.expect(foodRepo.loadFood(dish.getIngredient().getId())).andReturn(
					dish.getIngredient());
		}
		EasyMock.replay(foodRepo);

		MealMutablePropertyProviderAdapter adapter = new MealMutablePropertyProviderAdapter(request);
		adapter.setFoodRepository(foodRepo);

		assertEquals(request.getInstant(), adapter.getInstant());
		assertTrue(CollectionUtils.isEqualCollection(dishes.values(), adapter.getDishes()));
	}
}
