package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

public class MealPropertyProviderAdapterTest extends TestCase {
	public void testAdapt() {
		final Map<Long, PreparedFood> dishes = new HashMap<Long, PreparedFood>();
		dishes.put(Meals.redWineDrink().getId(), Meals.redWineDrink());
		dishes.put(Meals.spaghettiDish().getId(), Meals.spaghettiDish());

		MealCreationRequest request = new MealCreationRequest() {
			public Set<PreparedFoodCreationRequest> getDishCreationRequests() {
				Set<PreparedFoodCreationRequest> reqs = new HashSet<PreparedFoodCreationRequest>();
				for (final PreparedFood dish : dishes.values()) {
					reqs.add(new PreparedFoodCreationRequest() {
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

		MealPropertyProviderAdapter adapter = new MealPropertyProviderAdapter(request);
		adapter.setFoodRepository(foodRepo);

		assertEquals(request.getInstant(), adapter.getInstant());
		assertTrue(CollectionUtils.isEqualCollection(dishes.values(), adapter.getDishes()));
	}
}
