package com.gb1.healthcheck.services.meals.support;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

public class MealPropertyProviderAdapterTest extends TestCase {
	public void testAdapt() {
		final User eater = Users.gb();
		final Date instant = new Date();
		final Map<Long, PreparedFood> dishes = new HashMap<Long, PreparedFood>();
		dishes.put(Meals.redWineDrink().getId(), Meals.redWineDrink());
		dishes.put(Meals.spaghettiDish().getId(), Meals.spaghettiDish());

		MealCreationRequest request = new MealCreationRequest() {
			public Long getEaterId() {
				return eater.getId();
			}

			public Date getInstant() {
				return instant;
			}

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
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (PreparedFood dish : dishes.values()) {
			EasyMock.expect(foodRepo.loadFood(dish.getIngredient().getId())).andReturn(
					dish.getIngredient());
		}
		EasyMock.replay(foodRepo);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(eater.getId())).andReturn(eater);
		EasyMock.replay(userRepo);

		MealCreationPropertyProviderAdapter adapter = new MealCreationPropertyProviderAdapter(
				request);
		adapter.setFoodRepository(foodRepo);
		adapter.setUserRepository(userRepo);

		assertEquals(eater, adapter.getEater());
		assertEquals(request.getInstant(), adapter.getInstant());
		assertTrue(CollectionUtils.isEqualCollection(dishes.values(), adapter.getDishes()));
	}
}
