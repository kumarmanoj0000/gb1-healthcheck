package com.gb1.healthcheck.services.foods;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.foods.ComplexFoodUpdatePropertyProvider;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;

@Configurable("complexFoodUpdatePropertyProviderAdapter")
public class ComplexFoodUpdatePropertyProviderAdapter implements ComplexFoodUpdatePropertyProvider {
	private FoodRepository foodRepo;
	private ComplexFoodUpdateRequest request;

	public ComplexFoodUpdatePropertyProviderAdapter(ComplexFoodUpdateRequest request) {
		this.request = request;
	}

	public Set<Food> getIngredients() {
		Set<Food> ingredients = new HashSet<Food>();
		for (Long ingredientId : request.getIngredientIds()) {
			ingredients.add(foodRepo.loadFood(ingredientId));
		}

		return ingredients;
	}

	public String getName() {
		return request.getName();
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
