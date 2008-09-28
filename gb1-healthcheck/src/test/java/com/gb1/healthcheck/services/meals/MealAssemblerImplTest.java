package com.gb1.healthcheck.services.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class MealAssemblerImplTest {
	@Test
	public void testCreate() {
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

		MealAssemblerImpl assembler = new MealAssemblerImpl();
		assembler.foodRepo = foodRepo;
		assembler.userRepo = userRepo;

		Meal meal = assembler.createMeal(request);
		assertEquals(eater, meal.getEater());
		assertEquals(request.getInstant(), meal.getInstant());
		assertTrue(CollectionUtils.isEqualCollection(dishes.values(), meal.getDishes()));
	}

	@Test
	public void testUpdate() {
		final Meal meal = new Meal(Users.gb());

		final Date updatedInstant = new Date();
		final Map<Long, PreparedFood> dishes = new HashMap<Long, PreparedFood>();
		dishes.put(Meals.redWineDrink().getId(), Meals.redWineDrink());
		dishes.put(Meals.spaghettiDish().getId(), Meals.spaghettiDish());

		MealUpdateRequest request = new MealUpdateRequest() {
			public Long getMealId() {
				return meal.getId();
			}

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
				return updatedInstant;
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (PreparedFood dish : dishes.values()) {
			EasyMock.expect(foodRepo.loadFood(dish.getIngredient().getId())).andReturn(
					dish.getIngredient());
		}
		EasyMock.replay(foodRepo);

		MealAssemblerImpl assembler = new MealAssemblerImpl();
		assembler.foodRepo = foodRepo;
		assembler.updateMeal(meal, request);

		assertEquals(request.getInstant(), meal.getInstant());
		assertTrue(CollectionUtils.isEqualCollection(dishes.values(), meal.getDishes()));
	}
}
