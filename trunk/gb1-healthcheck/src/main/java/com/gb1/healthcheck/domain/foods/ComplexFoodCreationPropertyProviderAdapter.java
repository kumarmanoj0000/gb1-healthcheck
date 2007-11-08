package com.gb1.healthcheck.domain.foods;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable("complexFoodCreationPropertyProviderAdapter")
public class ComplexFoodCreationPropertyProviderAdapter implements
		ComplexFoodCreationPropertyProvider {
	private FoodRepository foodRepo;
	private ComplexFoodCreationRequest request;

	public ComplexFoodCreationPropertyProviderAdapter(ComplexFoodCreationRequest request) {
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