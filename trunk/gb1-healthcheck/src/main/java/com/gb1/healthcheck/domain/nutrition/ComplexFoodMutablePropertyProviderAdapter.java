package com.gb1.healthcheck.domain.nutrition;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable("complexFoodMutablePropertyProviderAdapter")
public class ComplexFoodMutablePropertyProviderAdapter implements
		ComplexFoodMutablePropertyProvider {
	private FoodRepository foodRepo;
	private ComplexFoodUpdateRequest request;

	public ComplexFoodMutablePropertyProviderAdapter(ComplexFoodUpdateRequest request) {
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
