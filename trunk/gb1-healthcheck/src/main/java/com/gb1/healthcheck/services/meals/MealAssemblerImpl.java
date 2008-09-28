package com.gb1.healthcheck.services.meals;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

@Component("mealAssembler")
public class MealAssemblerImpl implements MealAssembler {
	@Resource
	protected FoodRepository foodRepo;

	@Resource
	protected UserRepository userRepo;

	public MealAssemblerImpl() {
	}

	public Meal createMeal(MealCreationRequest request) {
		User eater = userRepo.loadUser(request.getEaterId());
		Meal meal = new Meal(eater, request.getInstant());

		for (PreparedFoodCreationRequest dishRequest : request.getDishCreationRequests()) {
			PreparedFood dish = new PreparedFood(foodRepo.loadFood(dishRequest.getIngredientId()),
					dishRequest.getPreparationMethod());
			meal.addDish(dish);
		}

		return meal;
	}

	public void updateMeal(Meal meal, MealUpdateRequest request) {
		meal.setInstant(request.getInstant());

		Set<PreparedFood> requestDishes = assembleDishes(request);
		Set<PreparedFood> dishesToAdd = identifyDishesToAdd(meal, requestDishes);
		Set<PreparedFood> dishesToRemove = identifyDishesToRemove(meal, requestDishes);

		for (PreparedFood dish : dishesToAdd) {
			meal.addDish(dish);
		}
		for (PreparedFood dish : dishesToRemove) {
			meal.removeDish(dish);
		}
	}

	private Set<PreparedFood> identifyDishesToAdd(Meal meal, Set<PreparedFood> requestDishes) {
		Set<PreparedFood> dishesToAdd = new HashSet<PreparedFood>();

		for (PreparedFood dish : requestDishes) {
			if (!meal.containsDish(dish)) {
				dishesToAdd.add(dish);
			}
		}

		return dishesToAdd;
	}

	private Set<PreparedFood> identifyDishesToRemove(Meal meal, Set<PreparedFood> requestDishes) {
		Set<PreparedFood> dishesToRemove = new HashSet<PreparedFood>();

		for (PreparedFood dish : meal.getDishes()) {
			if (!requestDishes.contains(dish)) {
				dishesToRemove.add(dish);
			}
		}

		return dishesToRemove;
	}

	private Set<PreparedFood> assembleDishes(MealUpdateRequest request) {
		Set<PreparedFood> requestDishes = new HashSet<PreparedFood>();

		for (PreparedFoodUpdateRequest dishRequest : request.getDishUpdateRequests()) {
			PreparedFood dish = new PreparedFood(foodRepo.loadFood(dishRequest.getIngredientId()),
					dishRequest.getPreparationMethod());
			requestDishes.add(dish);
		}

		return requestDishes;
	}
}
