package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;

@Configurable("mealUpdatePropertyProviderAdapter")
public class MealUpdatePropertyProviderAdapter implements MealUpdatePropertyProvider {
	private FoodRepository foodRepo;
	private MealUpdateRequest request;

	public MealUpdatePropertyProviderAdapter(MealUpdateRequest request) {
		this.request = request;
	}

	public Set<PreparedFood> getDishes() {
		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		for (PreparedFoodUpdateRequest dur : request.getDishUpdateRequests()) {
			Food ingredient = foodRepo.loadFood(dur.getIngredientId());
			dishes.add(new PreparedFood(ingredient, dur.getPreparationMethod()));
		}

		return dishes;
	}

	public Date getInstant() {
		return request.getInstant();
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
